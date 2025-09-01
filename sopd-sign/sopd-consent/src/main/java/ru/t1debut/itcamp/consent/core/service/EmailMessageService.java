package ru.t1debut.itcamp.consent.core.service;

import ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEmailMessageStatusEvent;
import ru.t1debut.itcamp.consent.model.consent.Consent;
import ru.t1debut.itcamp.consent.model.message.ConsentRequestEmailMessage;

import java.util.UUID;

public interface EmailMessageService {

    ConsentRequestEmailMessage getLastEmailMessage(Consent consent);

    ConsentRequestEmailMessage save(ConsentRequestEmailMessage consentRequestEmailMessage);

    void setMessageStatus(ConsentRequestEmailMessageStatusEvent consentRequestEmailMessageStatusEvent);

    ConsentRequestEmailMessage findConsentRequestEmailMessage(UUID id);
}
