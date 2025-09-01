package ru.t1debut.itcamp.consent.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEvent;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaProducerConfig {

    private final KafkaConfig kafkaConfig;

    @Bean
    public KafkaProducer<String, ConsentRequestEvent> consentRequestkafkaProducer() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(
                JsonSerializer.TYPE_MAPPINGS,
                "ConsentRequestEvent:ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEvent"
        );
        properties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new KafkaProducer<>(properties);
    }
}
