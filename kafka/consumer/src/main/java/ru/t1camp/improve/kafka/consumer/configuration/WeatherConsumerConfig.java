package ru.t1camp.improve.kafka.consumer.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.t1camp.improve.kafka.consumer.core.object.kafka.WeatherMessageKafka;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WeatherConsumerConfig {

    private final KafkaBootstrapServersConfig kafkaBootstrapServersConfig;

    @Bean
    public ConsumerFactory<String, WeatherMessageKafka> kafkaWeatherConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServersConfig.getBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, WeatherMessageKafka.class.getName());
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "ru.t1camp.improve.kafka.producer.core.object.kafka");
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, WeatherMessageKafka>> kafkaWeatherListenerContainerFactory(
            ConsumerFactory<String, WeatherMessageKafka> kafkaWeatherConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, WeatherMessageKafka> listenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(kafkaWeatherConsumerFactory);
        return listenerContainerFactory;
    }
}
