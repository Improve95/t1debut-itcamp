package ru.t1debut.itcamp.consent.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1debut.itcamp.consent.api.controller.spec.ConsentControllerSpec;
import ru.t1debut.itcamp.consent.api.dto.consent.CheckConsentLinkAccessResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.ConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.GetConsentRequest;
import ru.t1debut.itcamp.consent.api.dto.consent.GetConsentRequestEmailMessageStatusResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentRequest;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.create.CreateCandidateProfileRequest;
import ru.t1debut.itcamp.consent.core.service.ConsentService;
import ru.t1debut.itcamp.consent.model.consent.ConsentStatus;

import java.util.List;
import java.util.UUID;

import static ru.t1debut.itcamp.consent.api.ApiPath.ACCESS;
import static ru.t1debut.itcamp.consent.api.ApiPath.CONSENTS;
import static ru.t1debut.itcamp.consent.api.ApiPath.CONSENT_STATUS;
import static ru.t1debut.itcamp.consent.api.ApiPath.CONSENT_STATUS_PLACEHOLDER;
import static ru.t1debut.itcamp.consent.api.ApiPath.CONSENT_UUID_PLACEHOLDER;
import static ru.t1debut.itcamp.consent.api.ApiPath.EMAIL_MESSAGE;
import static ru.t1debut.itcamp.consent.api.ApiPath.PARAMETRIZED;
import static ru.t1debut.itcamp.consent.api.ApiPath.SEND;
import static ru.t1debut.itcamp.consent.api.ApiPath.STATUS;

@RequiredArgsConstructor
@RestController
@RequestMapping(CONSENTS)
public class ConsentController implements ConsentControllerSpec {

    private final ConsentService consentService;

    @GetMapping(CONSENT_UUID_PLACEHOLDER + ACCESS)
    public ResponseEntity<CheckConsentLinkAccessResponse> checkLinkAccess(
            @PathVariable @Valid @NotNull UUID consentId
    ) {
        var checkConsentLinkAccessResponse = consentService.checkConsentLinkAccess(consentId);
        return ResponseEntity.ok(checkConsentLinkAccessResponse);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping(CONSENT_UUID_PLACEHOLDER + EMAIL_MESSAGE + STATUS)
    public ResponseEntity<GetConsentRequestEmailMessageStatusResponse> getConsentRequestEmailMessageStatus(
            @PathVariable @Valid @NotNull UUID consentId
    ) {
        var response = consentService.getConsentRequestEmailMessageStatus(consentId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping(PARAMETRIZED)
    public ResponseEntity<List<ConsentResponse>> getConsents(@RequestBody @Valid GetConsentRequest getConsentRequest) {
        var consentsResponse = consentService.getConsents(getConsentRequest);
        return ResponseEntity.ok(consentsResponse);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping(SEND)
    public ResponseEntity<SendConsentResponse> sendSopdToCandidate(
            @RequestBody @Valid SendConsentRequest sendConsentRequest
    ) {
        var sendConsentResponse = consentService.sendSopd(sendConsentRequest);
        return ResponseEntity.ok(sendConsentResponse);
    }

    @PostMapping(CONSENT_UUID_PLACEHOLDER + CONSENT_STATUS + CONSENT_STATUS_PLACEHOLDER)
    public ResponseEntity<Void> setConsentStatusAndSaveCandidateProfile(
            @PathVariable @Valid @NotNull UUID consentId,
            @PathVariable @Valid @NotNull ConsentStatus consentStatus,
            @RequestBody @Valid CreateCandidateProfileRequest createCandidateProfileRequest
    ) {
        consentService.setConsentStatusAndSaveCandidateProfile(consentId, consentStatus, createCandidateProfileRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
