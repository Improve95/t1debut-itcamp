package ru.t1debut.itcamp.consent.core.service;

import ru.t1debut.itcamp.consent.api.dto.consent.CheckConsentLinkAccessResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.ConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.GetConsentRequest;
import ru.t1debut.itcamp.consent.api.dto.consent.GetConsentRequestEmailMessageStatusResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentRequest;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.create.CreateCandidateProfileRequest;
import ru.t1debut.itcamp.consent.model.consent.Consent;
import ru.t1debut.itcamp.consent.model.consent.ConsentStatus;

import java.util.List;
import java.util.UUID;

public interface ConsentService {

    List<ConsentResponse> getConsents(GetConsentRequest consentRequest);

    GetConsentRequestEmailMessageStatusResponse getConsentRequestEmailMessageStatus(UUID consentId);

    UUID saveConsent(Consent consent);

    SendConsentResponse sendSopd(SendConsentRequest sendConsentRequest);

    void setConsentStatusAndSaveCandidateProfile(
            UUID consentId,
            ConsentStatus consentStatus,
            CreateCandidateProfileRequest createCandidateProfileRequest
    );

    CheckConsentLinkAccessResponse checkConsentLinkAccess(UUID id);

    Consent findConsent(UUID id);
}
