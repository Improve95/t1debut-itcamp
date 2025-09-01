package ru.t1debut.itcamp.consent.core.kafka;

import ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEvent;

public interface KafkaProducerService {

    void sendConsentRequest(ConsentRequestEvent consentRequestEvent);
}
