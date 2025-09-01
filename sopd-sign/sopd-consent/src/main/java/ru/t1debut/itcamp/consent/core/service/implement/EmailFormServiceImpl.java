package ru.t1debut.itcamp.consent.core.service.implement;

import com.google.common.hash.Hashing;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.api.dto.email.UpdateEmailFormRequest;
import ru.t1debut.itcamp.consent.api.dto.email.UpdateEmailFormResponse;
import ru.t1debut.itcamp.consent.api.exception.ServiceException;
import ru.t1debut.itcamp.consent.configuration.storage.S3StorageConfig;
import ru.t1debut.itcamp.consent.core.dao.repository.EmailFormRepository;
import ru.t1debut.itcamp.consent.core.service.DocumentsPrimeVersionService;
import ru.t1debut.itcamp.consent.core.service.EmailFormService;
import ru.t1debut.itcamp.consent.core.service.S3StorageService;
import ru.t1debut.itcamp.consent.model.EmailForm;
import ru.t1debut.itcamp.consent.util.DatabaseUtil;
import ru.t1debut.itcamp.consent.util.DocumentsUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ALREADY_EXIST;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.NOT_FOUND;
import static ru.t1debut.itcamp.consent.util.DocumentsUtil.EMAIL_FORM_NAME;
import static ru.t1debut.itcamp.consent.util.DocumentsUtil.HTML_EXTENSION;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailFormServiceImpl implements EmailFormService {

    private final DocumentsPrimeVersionService documentsPrimeVersionService;

    private final S3StorageService s3StorageService;

    private final EmailFormRepository emailFormRepository;

    private final S3StorageConfig s3StorageConfig;

    private final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

    @Transactional
    @Override
    public List<Integer> getAllEmailFormVersions() {
        return emailFormRepository.findAll().stream()
                .map(EmailForm::getVersion)
                .toList();
    }

    @Transactional
    @Override
    public EmailForm getPrimeEmailForm() {
        return documentsPrimeVersionService.getPrimeEmailForm();
    }

    @Transactional
    @Override
    public Resource getEmailFormResource(int version) {
        return this.getEmailFormResource(findEmailForm(version));
    }

    @Override
    public Resource getEmailFormResource(EmailForm emailForm) {
        log.info("try download email form with key: {}", emailForm.getKey());
        return s3StorageService.download(s3StorageConfig.getMinio().emailFormBucketName(), emailForm.getKey());
    }

    @Override
    public String getFormattedEmailForm(EmailForm emailForm, String... args) {
        log.info("try get formatted email form version: {} key: {}", emailForm.getVersion(), emailForm.getKey());
        Resource emailFormS3Resource = getEmailFormResource(emailForm);
        log.info("got email form resource with filename: {}", emailFormS3Resource.getFilename());
        try (InputStream emailFormInputStream = emailFormS3Resource.getInputStream()) {
            byte[] bytes = emailFormInputStream.readAllBytes();
            log.info("got bytes for email form, version: {}, length: {}", emailForm.getVersion(), bytes.length);
            String formatedEmail = decoder.decode(ByteBuffer.wrap(bytes))
                    .toString()
                    .replace("%s", args[0]);
            log.info("email success formated with args: {}", Arrays.toString(args));
            return formatedEmail;
        } catch (IOException ex) {
            log.error(
                    "error in getFormattedEmailForm form version: {} key: {}, args: {}, ex: {}",
                    emailForm.getVersion(),
                    emailForm.getKey(),
                    Arrays.toString(args),
                    ex.getLocalizedMessage()
            );
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    @Override
    public UpdateEmailFormResponse createEmailFormNewVersion(UpdateEmailFormRequest updateEmailFormRequest) {
        String htmlEmailBody = updateEmailFormRequest.getHtmlEmailBody();
        if (!DocumentsUtil.isValidHtml(htmlEmailBody)) {
            throw new ServiceException(ILLEGAL_VALUE, "htmlEmailBody");
        }

        String hash = Hashing.sha256()
                .hashString(htmlEmailBody, StandardCharsets.UTF_8)
                .toString();

        String fileKey = EMAIL_FORM_NAME + hash + HTML_EXTENSION;
        EmailForm emailForm = EmailForm.builder()
                .key(fileKey)
                .build();
        try {
            emailForm = emailFormRepository.save(emailForm);
        } catch (DataIntegrityViolationException ex) {
            if (DatabaseUtil.isUniqueConstraintException(ex)) {
                throw new ServiceException(ALREADY_EXIST, "htmlEmailBody", "html body");
            }
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }

        if (updateEmailFormRequest.isSetPrime()) {
            documentsPrimeVersionService.setPrimeEmailForm(emailForm);
        }

        try (InputStream emailHtmlFotmInputStream = new ByteArrayInputStream(
                htmlEmailBody.getBytes(StandardCharsets.UTF_8)
        )) {
            s3StorageService.upload(
                    emailHtmlFotmInputStream,
                    s3StorageConfig.getMinio().emailFormBucketName(),
                    fileKey
            );
        } catch (IOException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }

        return UpdateEmailFormResponse.builder()
                .version(emailForm.getVersion())
                .build();
    }

    @Transactional
    @Override
    public EmailForm findEmailForm(int version) {
        return emailFormRepository.findEmailFormByVersion(version)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "htmlEmailForm", "version"));
    }
}
