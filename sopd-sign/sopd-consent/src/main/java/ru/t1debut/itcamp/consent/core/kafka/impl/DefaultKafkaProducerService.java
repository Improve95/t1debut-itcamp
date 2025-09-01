package ru.t1debut.itcamp.consent.core.kafka.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.configuration.kafka.KafkaConfig;
import ru.t1debut.itcamp.consent.core.kafka.KafkaProducerService;
import ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEvent;

@RequiredArgsConstructor
@Service
public class DefaultKafkaProducerService implements KafkaProducerService {

    private final KafkaConfig kafkaConfig;

    private final KafkaProducer<String, ConsentRequestEvent> consentRequestKafkaProducer;

    @Override
    public void sendConsentRequest(ConsentRequestEvent consentRequestEvent) {
        consentRequestKafkaProducer.send(
                new ProducerRecord<>(kafkaConfig.getConsentRequestEmailMessageTopic(), consentRequestEvent)
        );
    }
}
