package ru.t1debut.itcamp.consent.core.service.implement;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.api.dto.consent.CheckConsentLinkAccessResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.ConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.GetConsentRequest;
import ru.t1debut.itcamp.consent.api.dto.consent.GetConsentRequestEmailMessageStatusResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentRequest;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.create.CreateCandidateProfileRequest;
import ru.t1debut.itcamp.consent.api.exception.ServiceException;
import ru.t1debut.itcamp.consent.configuration.consent.ConsentConfig;
import ru.t1debut.itcamp.consent.configuration.external.ExternalClientDatasource;
import ru.t1debut.itcamp.consent.core.dao.query.ConsentQuery;
import ru.t1debut.itcamp.consent.core.dao.repository.ConsentRepository;
import ru.t1debut.itcamp.consent.core.external.client.service.ExternalAuthClient;
import ru.t1debut.itcamp.consent.core.kafka.KafkaProducerService;
import ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEvent;
import ru.t1debut.itcamp.consent.core.service.CandidateProfileService;
import ru.t1debut.itcamp.consent.core.service.ConsentService;
import ru.t1debut.itcamp.consent.core.service.EmailFormService;
import ru.t1debut.itcamp.consent.core.service.EmailMessageService;
import ru.t1debut.itcamp.consent.core.service.SopdDocumentService;
import ru.t1debut.itcamp.consent.core.service.UniqueLinkIdGeneratorService;
import ru.t1debut.itcamp.consent.model.CandidateProfile;
import ru.t1debut.itcamp.consent.model.EmailForm;
import ru.t1debut.itcamp.consent.model.SopdDocument;
import ru.t1debut.itcamp.consent.model.consent.Consent;
import ru.t1debut.itcamp.consent.model.consent.ConsentStatus;
import ru.t1debut.itcamp.consent.model.message.ConsentRequestEmailMessage;
import ru.t1debut.itcamp.consent.util.SecurityUtil;
import ru.t1debut.itcamp.consent.util.mapper.ConsentMapper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ALREADY_EXIST;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.EXPIRED;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.INVALID_REQUEST;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.NOT_FOUND;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SOPD_EMAIL_DEFAULT_SUBJECT;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsentServiceImpl implements ConsentService {

    private final CandidateProfileService candidateProfileService;

    private final SopdDocumentService sopdDocumentService;

    private final EmailFormService emailFormService;

    private final KafkaProducerService kafkaProducerService;

    private final EmailMessageService emailMessageService;

    private final UniqueLinkIdGeneratorService uniqueLinkIdGeneratorService;

    private final ExternalAuthClient externalAuthClient;

    private final ConsentRepository consentRepository;

    private final EntityManager em;

    private final ExternalClientDatasource clientDatasource;

    private final ConsentConfig consentConfig;

    private final ConsentMapper consentMapper;

    private final MessageSource messageSource;

    @Transactional
    @Override
    public List<ConsentResponse> getConsents(GetConsentRequest consentRequest) {
        return ConsentQuery.getConsents(
                em,
                consentRequest.getId(),
                consentRequest.getManagerId(),
                consentRequest.getStatus(),
                consentRequest.getCandidateEmail()
        ).stream()
                .map(consentMapper::toConsentResponse)
                .toList();
    }

    @Transactional
    @Override
    public GetConsentRequestEmailMessageStatusResponse getConsentRequestEmailMessageStatus(UUID consentId) {
        Consent consent = findConsent(consentId);
        ConsentRequestEmailMessage consentRequestEmailMessage =
                emailMessageService.getLastEmailMessage(consent);

        return GetConsentRequestEmailMessageStatusResponse.builder()
                .status(consentRequestEmailMessage.getStatus())
                .build();
    }

    @Transactional
    @Override
    public UUID saveConsent(Consent consent) {
        return consentRepository.save(consent).getId();
    }

    @Transactional
    @Override
    public SendConsentResponse sendSopd(SendConsentRequest sendConsentRequest) {
        log.info("send sopd: {}", sendConsentRequest);
        canCreateNewConsent(sendConsentRequest.getCandidateEmail());

        if (!externalAuthClient.emailExistRequest(sendConsentRequest.getCandidateEmail(), SecurityUtil.getSessionToken())) {
            log.info("consent with this email already exist: {}", sendConsentRequest.getCandidateEmail());
            throw new ServiceException(ILLEGAL_VALUE, "email");
        }

        SopdDocument sopdDocument;
        if (sendConsentRequest.getSopdDocumentVersion() == null) {
            sopdDocument = sopdDocumentService.getPrimeSopdDocument();
            log.info("use prime sopd document version: {}", sopdDocument.getVersion());
        } else {
            sopdDocument = sopdDocumentService.findSopdDocument(sendConsentRequest.getSopdDocumentVersion());
            log.info("use custom sopd document version: {}", sendConsentRequest.getSopdDocumentVersion());
        }

        UUID consentId = uniqueLinkIdGeneratorService.getRandomUuid();
        CandidateProfile candidateProfile = getOrCreateCandidateProfile(sendConsentRequest.getCandidateEmail());
        Consent savedConsent = consentRepository.save(Consent.builder()
                .id(consentId)
                .sopdDocument(sopdDocument)
                .managerId(SecurityUtil.getRequestUserIdByAuth())
                .candidateProfile(candidateProfile)
                .build());
        log.info("save profile with id: {}, email: {}", candidateProfile.getId(), candidateProfile.getEmail());
        log.info("save consent with id: {}", savedConsent.getId());

        String htmlEmailForm = generateEmailMessageHtmlBody(consentId);
        ConsentRequestEmailMessage savedEmailMessage = emailMessageService.save(ConsentRequestEmailMessage.builder()
                .consent(savedConsent)
                .build()
        );
        log.info("save email message with id: {}", savedEmailMessage.getId());

        kafkaProducerService.sendConsentRequest(
                ConsentRequestEvent.builder()
                        .from(SecurityUtil.getRequestUserClaim().getEmail())
                        .to(sendConsentRequest.getCandidateEmail())
                        .title(messageSource.getMessage(
                                SOPD_EMAIL_DEFAULT_SUBJECT,
                                null,
                                LocaleContextHolder.getLocale()))
                        .body(htmlEmailForm)
                        .messageId(savedEmailMessage.getId())
                        .isHtmlBody(true)
                        .build()
        );

        return consentMapper.toSendConsentResponse(savedConsent);
    }

    @Transactional
    @Override
    public void setConsentStatusAndSaveCandidateProfile(
            UUID consentId,
            ConsentStatus consentStatus,
            CreateCandidateProfileRequest createCandidateProfileRequest
    ) {
        if (consentStatus != ConsentStatus.APPROVE && consentStatus != ConsentStatus.REJECT) {
            throw new ServiceException(ILLEGAL_VALUE, "consentStatus");
        }

        Consent consent = findConsent(consentId);
        isConsentWaitingAnswerAndNotExpired(consent);

        consent.setStatus(consentStatus);

        candidateProfileService.patchCandidateProfile(consent.getCandidateProfile(), createCandidateProfileRequest);
    }

    @Transactional
    @Override
    public CheckConsentLinkAccessResponse checkConsentLinkAccess(UUID id) {
        Consent consent = findConsent(id);
        isConsentWaitingAnswerAndNotExpired(consent);
        String email = consent.getCandidateProfile().getEmail();
        return CheckConsentLinkAccessResponse.builder()
                .email(email)
                .sopdVersion(consent.getSopdDocumentVersion())
                .build();
    }

    @Transactional
    @Override
    public Consent findConsent(UUID id) {
        return consentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "consent", "id"));
    }

    private void canCreateNewConsent(String email) {
        log.debug("canCreateNewConsent {}", email);
        var consents = consentRepository.findConsentByStatusAndCreatedAtAfterAndCandidateProfile_Email(
                ConsentStatus.WAITING,
                Instant.now().minus(consentConfig.getConsent().requestDuration()),
                email
        );
        if (!consents.isEmpty()) {
            log.info("consent already exist: {}", email);
            throw new ServiceException(ALREADY_EXIST, "consent", "email and no answered");
        }
    }

    private CandidateProfile getOrCreateCandidateProfile(String candidateEmail) {
        Optional<CandidateProfile> candidateProfileOptional = candidateProfileService
                .getOptionalCandidateProfile(candidateEmail);
        log.info(candidateProfileOptional
                .map(candidateProfile -> "existing candidate profile with email: " + candidateProfile.getEmail())
                .orElse("create new candidate profile with email:" + candidateEmail)
        );
        return candidateProfileService.getOptionalCandidateProfile(candidateEmail)
                .orElse(
                        CandidateProfile.builder()
                                .email(candidateEmail)
                                .build()
                );
    }

    private String generateEmailMessageHtmlBody(UUID consentId) {
        log.info("generate email message with consent id {}", consentId);
        EmailForm emailForm = emailFormService.getPrimeEmailForm();
        log.debug("prime email: {}", emailForm);
        return emailFormService.getFormattedEmailForm(
                emailForm,
                clientDatasource.getFrontend().url() + "/" + consentId
        );
    }

    private void isConsentWaitingAnswerAndNotExpired(Consent consent) {
        if (consent.getStatus() == ConsentStatus.APPROVE || consent.getStatus() == ConsentStatus.REJECT) {
            throw new ServiceException(INVALID_REQUEST, "this consent already get answer");
        }

        if (consent.getCreatedAt().plus(consentConfig.getConsent().requestDuration()).isBefore(Instant.now())) {
            consent.setStatus(ConsentStatus.EXPIRED);
            throw new ServiceException(EXPIRED, "consent link");
        }
    }
}
