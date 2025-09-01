package ru.t1debut.itcamp.consent.core.service.implement;

import com.google.common.hash.Hashing;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.api.dto.sopd.UpdateSopdDocumentRequest;
import ru.t1debut.itcamp.consent.api.dto.sopd.UpdateSopdDocumentResponse;
import ru.t1debut.itcamp.consent.api.exception.ServiceException;
import ru.t1debut.itcamp.consent.configuration.storage.S3StorageConfig;
import ru.t1debut.itcamp.consent.core.dao.repository.SopdDocumentRepository;
import ru.t1debut.itcamp.consent.core.service.DocumentsPrimeVersionService;
import ru.t1debut.itcamp.consent.core.service.S3StorageService;
import ru.t1debut.itcamp.consent.core.service.SopdDocumentService;
import ru.t1debut.itcamp.consent.model.SopdDocument;
import ru.t1debut.itcamp.consent.util.DatabaseUtil;
import ru.t1debut.itcamp.consent.util.DocumentsUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ALREADY_EXIST;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.NOT_FOUND;
import static ru.t1debut.itcamp.consent.util.DocumentsUtil.HTML_EXTENSION;
import static ru.t1debut.itcamp.consent.util.DocumentsUtil.SOPD_DOCUMENT_NAME;

@RequiredArgsConstructor
@Service
public class SopdDocumentServiceImpl implements SopdDocumentService {

    private final DocumentsPrimeVersionService documentsPrimeVersionService;

    private final S3StorageService s3StorageService;

    private final SopdDocumentRepository sopdDocumentRepository;

    private final S3StorageConfig s3StorageConfig;

    @Transactional
    @Override
    public SopdDocument getPrimeSopdDocument() {
        return documentsPrimeVersionService.getPrimeSopdDocument();
    }

    @Transactional
    @Override
    public List<Integer> getAllSopdVersions() {
        return sopdDocumentRepository.findAll().stream()
                .map(SopdDocument::getVersion)
                .toList();
    }

    @Transactional
    @Override
    public Resource getSopdDocumentResource(int version) {
        return getSopdDocumentResource(findSopdDocument(version));
    }

    @Override
    public Resource getSopdDocumentResource(SopdDocument sopdDocument) {
        return s3StorageService.download(s3StorageConfig.getMinio().sopdDocumentBucketName(), sopdDocument.getKey());
    }

    @Override
    public UpdateSopdDocumentResponse createSopdDocument(UpdateSopdDocumentRequest updateSopdDocumentRequest) {
        String sopdDocumentHtmlBody = updateSopdDocumentRequest.getSopdDocumentHtmlBody();
        if (!DocumentsUtil.isValidHtml(sopdDocumentHtmlBody)) {
            throw new ServiceException(ILLEGAL_VALUE, "sopd document html");
        }

        String hash = Hashing.sha256()
                .hashString(sopdDocumentHtmlBody, StandardCharsets.UTF_8)
                .toString();

        String fileKey = SOPD_DOCUMENT_NAME + hash + HTML_EXTENSION;
        SopdDocument sopdDocument = SopdDocument.builder()
                .key(fileKey)
                .build();

        try {
            sopdDocument = sopdDocumentRepository.save(sopdDocument);
        } catch (DataIntegrityViolationException ex) {
            if (DatabaseUtil.isUniqueConstraintException(ex)) {
                throw new ServiceException(ALREADY_EXIST, "sopd document html", "html body");
            }
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }

        if (updateSopdDocumentRequest.isSetPrime()) {
            documentsPrimeVersionService.setPrimeSopdDocument(sopdDocument);
        }

        try (InputStream sopdDocumentHtmlInputStream = new ByteArrayInputStream(
                sopdDocumentHtmlBody.getBytes(StandardCharsets.UTF_8)
        )) {
            s3StorageService.upload(
                    sopdDocumentHtmlInputStream,
                    s3StorageConfig.getMinio().sopdDocumentBucketName(),
                    fileKey
            );
        } catch (IOException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }

        return UpdateSopdDocumentResponse.builder()
                .version(sopdDocument.getVersion())
                .build();
    }

    @Transactional
    @Override
    public SopdDocument findSopdDocument(int version) {
        return sopdDocumentRepository.findSopdDocumentByVersion(version)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "sopd document", "version"));
    }
}
