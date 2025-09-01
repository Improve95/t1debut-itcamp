package ru.t1debut.itcamp.notification.service.operation.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.notification.service.domain.EmailStatusEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailStatusProducer {

    private final KafkaTemplate<String, EmailStatusEvent> kafkaTemplate;

    @Value("${app.kafka.status-topic}")
    private String statusTopic;

    public void send(EmailStatusEvent event) {
        String key = event.getMessageId() != null ? event.getMessageId().toString() : null;
        assert key != null;
        kafkaTemplate.send(statusTopic, key, event)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish mail status for key={}: {}", key, ex.getMessage(), ex);
                    } else {
                        log.info("Published mail status to topic={}, key={}, partition={}, offset={}",
                                statusTopic, key,
                                res.getRecordMetadata().partition(),
                                res.getRecordMetadata().offset());
                    }
                });
    }
}
