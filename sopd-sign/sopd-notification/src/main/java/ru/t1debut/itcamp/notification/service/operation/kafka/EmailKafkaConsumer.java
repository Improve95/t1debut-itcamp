package ru.t1debut.itcamp.notification.service.operation.kafka;

import ru.t1debut.itcamp.notification.service.domain.EmailStatusEvent;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1debut.itcamp.notification.service.domain.EmailMessage;
import ru.t1debut.itcamp.notification.service.domain.EmailMessageDto;
import ru.t1debut.itcamp.notification.service.domain.EmailStatus;
import ru.t1debut.itcamp.notification.service.infrastructure.EmailMessageRepository;
import ru.t1debut.itcamp.notification.service.operation.email.EmailSenderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailKafkaConsumer {

    private final EmailSenderService mailSender;
    private final EmailMessageRepository emailMessageRepository;
    private final EmailStatusProducer emailStatusProducer;

    @KafkaListener(
            topics = "${app.kafka.email-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onMessage(@Valid @Payload EmailMessageDto msg) {
        log.info("Got mail event: to={}, subject={}", msg.to(), msg.title());

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setId(msg.messageId());
        emailMessage.setFrom(msg.from());
        emailMessage.setTo(msg.to());
        emailMessage.setTitle(msg.title());
        emailMessage.setBody(msg.body());
        emailMessage.setStatus(EmailStatus.WAITING);

        EmailMessage savedEmailMessage = emailMessageRepository.save(emailMessage);

        try {
            mailSender.send(msg);
            log.info("Sent to {}", msg.to());

            savedEmailMessage.setStatus(EmailStatus.DELIVERED);
            emailMessageRepository.save(savedEmailMessage);

            emailStatusProducer.send(
                    EmailStatusEvent.builder()
                            .messageId(msg.messageId())
                            .status(EmailStatus.DELIVERED)
                            .build()
            );

        } catch (Exception ex) {
            log.error("Send failed to {}: {}", msg.to(), ex.getMessage(), ex);

            savedEmailMessage.setStatus(EmailStatus.CANCELED);
            emailMessageRepository.save(savedEmailMessage);

            emailStatusProducer.send(
                    EmailStatusEvent.builder()
                            .messageId(msg.messageId())
                            .status(EmailStatus.CANCELED)
                            .build()
            );
        }
    }
}

