package ru.t1debut.itcamp.consent.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.t1debut.itcamp.consent.core.kafka.dto.ConsentRequestEmailMessageStatusEvent;
import ru.t1debut.itcamp.consent.core.kafka.exception.ConsumerExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerConfig {

    private final KafkaConfig kafkaConfig;

    private final ConsumerExceptionHandler consumerExceptionHandler;

    @Bean
    public ConsumerFactory<String, ConsentRequestEmailMessageStatusEvent> consentRequestEmailMessageStatusConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, ConsentRequestEmailMessageStatusEvent.class.getName());
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConsentRequestEmailMessageStatusEvent> consentRequestEmailMessageStatusListenerFactory(
            ConsumerFactory<String, ConsentRequestEmailMessageStatusEvent> consentRequestEmailMessageStatusConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, ConsentRequestEmailMessageStatusEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consentRequestEmailMessageStatusConsumerFactory);
        factory.setCommonErrorHandler(consumerExceptionHandler);
        return factory;
    }
}
