package ru.t1debut.itcamp.consent.core.service;

import org.springframework.core.io.Resource;
import ru.t1debut.itcamp.consent.api.dto.email.UpdateEmailFormRequest;
import ru.t1debut.itcamp.consent.api.dto.email.UpdateEmailFormResponse;
import ru.t1debut.itcamp.consent.model.EmailForm;

import java.util.List;

public interface EmailFormService {

    List<Integer> getAllEmailFormVersions();

    EmailForm getPrimeEmailForm();

    String getFormattedEmailForm(EmailForm emailForm, String... args);

    Resource getEmailFormResource(int version);

    Resource getEmailFormResource(EmailForm emailForm);

    UpdateEmailFormResponse createEmailFormNewVersion(UpdateEmailFormRequest updateEmailFormRequest);

    EmailForm findEmailForm(int version);
}
