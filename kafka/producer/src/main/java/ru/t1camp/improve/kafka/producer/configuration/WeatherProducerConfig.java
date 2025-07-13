package ru.t1camp.improve.kafka.producer.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.t1camp.improve.kafka.producer.core.object.kafka.WeatherMessageKafka;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WeatherProducerConfig {

    private final KafkaConfig kafkaConfig;

    @Bean
    public KafkaProducer<String, WeatherMessageKafka> kafkaProducer() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(JsonSerializer.TYPE_MAPPINGS, "WeatherMessageKafka:ru.t1camp.improve.kafka.producer.core.object.kafka.WeatherMessageKafka");
        properties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new KafkaProducer<>(properties);
    }
}
