package ru.t1camp.improve.kafka.producer.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.t1camp.improve.kafka.producer.core.object.kafka.WeatherMessageKafka;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WeatherProducerConfig {

    private final KafkaBootstrapServersConfig kafkaBootstrapServersConfig;

    @Bean
    public ProducerFactory<String, WeatherMessageKafka> kafkaWeatherProducerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServersConfig.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, WeatherMessageKafka> kafkaWeatherProducer(
            ProducerFactory<String, WeatherMessageKafka> kafkaWeatherProducerFactory
    ) {
        return new KafkaTemplate<>(kafkaWeatherProducerFactory);
    }
}
