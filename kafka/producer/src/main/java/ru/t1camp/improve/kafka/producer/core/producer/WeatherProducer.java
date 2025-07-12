package ru.t1camp.improve.kafka.producer.core.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.t1camp.improve.kafka.producer.core.object.kafka.WeatherMessageKafka;

import static ru.t1camp.improve.kafka.producer.util.KafkaUtil.WEATHER_TOPIC;

@RequiredArgsConstructor
@Component
public class WeatherProducer {

    private final KafkaTemplate<String, WeatherMessageKafka> weatherKafkaTemplate;

    public void sendMessageInWeatherTopic(WeatherMessageKafka weatherMessageKafka) {
        weatherKafkaTemplate.send(WEATHER_TOPIC, weatherMessageKafka);
    }
}
