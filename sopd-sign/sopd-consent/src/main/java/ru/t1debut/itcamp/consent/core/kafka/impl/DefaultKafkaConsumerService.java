package ru.t1debut.itcamp.consent.core.kafka.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.core.kafka.KafkaConsumerService;
import ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEmailMessageStatusEvent;
import ru.t1debut.itcamp.consent.core.service.EmailMessageService;

import static ru.t1debut.itcamp.consent.util.ExceptionUtil.createExceptionMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultKafkaConsumerService /*extends AbstractConsumerSeekAware */implements KafkaConsumerService, ConsumerSeekAware {

    private final EmailMessageService emailMessageService;

    @KafkaListener(
            topics = "${spring.kafka.consent-request-email-message-status-topic}",
            groupId = "${spring.kafka.consent-request-email-message-status-group-id}",
            containerFactory = "consentRequestEmailMessageStatusListenerFactory"
    )
    private void listenConsentRequestEmailMessageStatusTopic(@Payload ConsentRequestEmailMessageStatusEvent event) {
        log.info("receive message {}", event);
        try {
            emailMessageService.setMessageStatus(event);
        } catch (Exception ex) {
            log.error(createExceptionMessage(ex, "error on set message status"));
        }
    }
}
