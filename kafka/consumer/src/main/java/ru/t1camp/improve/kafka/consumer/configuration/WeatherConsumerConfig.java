package ru.t1camp.improve.kafka.consumer.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.t1camp.improve.kafka.consumer.core.object.kafka.WeatherMessageKafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WeatherConsumerConfig {

    private final KafkaConfig kafkaConfig;

    @Bean
    public KafkaConsumer<String, WeatherMessageKafka> weatherKafkaConsumer() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getWeatherGroupId());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, WeatherMessageKafka.class.getName());
        KafkaConsumer<String, WeatherMessageKafka> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(kafkaConfig.getWeatherTopic()));
        return consumer;
    }
}
