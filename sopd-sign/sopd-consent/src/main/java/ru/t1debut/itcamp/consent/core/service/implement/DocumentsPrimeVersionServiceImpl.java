package ru.t1debut.itcamp.consent.core.service.implement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.core.dao.repository.SopdDocumentPrimeVersionRepository;
import ru.t1debut.itcamp.consent.core.service.DocumentsPrimeVersionService;
import ru.t1debut.itcamp.consent.model.EmailForm;
import ru.t1debut.itcamp.consent.model.SopdDocument;

@RequiredArgsConstructor
@Service
public class DocumentsPrimeVersionServiceImpl implements DocumentsPrimeVersionService {

    private final SopdDocumentPrimeVersionRepository sopdDocumentPrimeVersionRepository;

    @Transactional
    @Override
    public void setPrimeSopdDocument(SopdDocument sopdDocument) {
        sopdDocumentPrimeVersionRepository.findAll().get(0)
                .setPrimeSopdDocument(sopdDocument);
    }

    @Transactional
    @Override
    public void setPrimeEmailForm(EmailForm emailForm) {
        sopdDocumentPrimeVersionRepository.findAll().get(0)
                .setPrimeEmailForm(emailForm);
    }

    @Transactional
    @Override
    public SopdDocument getPrimeSopdDocument() {
         return sopdDocumentPrimeVersionRepository.findAll()
                 .get(0)
                 .getPrimeSopdDocument();
    }

    @Transactional
    @Override
    public EmailForm getPrimeEmailForm() {
        return sopdDocumentPrimeVersionRepository.findAll()
                .get(0)
                .getPrimeEmailForm();
    }
}
