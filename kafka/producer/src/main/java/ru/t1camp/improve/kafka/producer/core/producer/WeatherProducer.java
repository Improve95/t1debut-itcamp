package ru.t1camp.improve.kafka.producer.core.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.t1camp.improve.kafka.producer.core.object.kafka.WeatherMessageKafka;

import static ru.t1camp.improve.kafka.producer.util.KafkaUtil.WEATHER_TOPIC;

@RequiredArgsConstructor
@Slf4j
@Component
public class WeatherProducer {

    private final KafkaTemplate<String, WeatherMessageKafka> weatherKafkaTemplate;

    public void sendMessageInWeatherTopic(WeatherMessageKafka weatherMessageKafka) {
        log.debug("weather event send: {}", weatherMessageKafka);
        weatherKafkaTemplate.send(WEATHER_TOPIC, weatherMessageKafka);
    }
}
