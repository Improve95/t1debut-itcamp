package ru.t1camp.improve.kafka.producer.core.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.t1camp.improve.kafka.producer.configuration.KafkaConfig;
import ru.t1camp.improve.kafka.producer.core.object.kafka.WeatherMessageKafka;

@RequiredArgsConstructor
@Slf4j
@Component
public class WeatherProducer {

    private final KafkaConfig kafkaConfig;

    private final KafkaProducer<String, WeatherMessageKafka> weatherKafkaProducer;

    public void sendMessageInWeatherTopic(WeatherMessageKafka weatherMessageKafka) {
        weatherKafkaProducer.send(new ProducerRecord<>(kafkaConfig.getWeatherTopic(), weatherMessageKafka));
        log.debug("weather event send: {}", weatherMessageKafka);
    }
}
