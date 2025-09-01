package ru.t1debut.itcamp.consent.core.service;

import ru.t1debut.itcamp.consent.model.EmailForm;
import ru.t1debut.itcamp.consent.model.SopdDocument;

public interface DocumentsPrimeVersionService {

    void setPrimeSopdDocument(SopdDocument sopdDocument);

    void setPrimeEmailForm(EmailForm emailForm);

    SopdDocument getPrimeSopdDocument();

    EmailForm getPrimeEmailForm();
}
