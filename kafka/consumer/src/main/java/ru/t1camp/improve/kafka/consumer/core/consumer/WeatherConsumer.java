package ru.t1camp.improve.kafka.consumer.core.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1camp.improve.kafka.consumer.core.object.kafka.WeatherMessageKafka;

import static ru.t1camp.improve.kafka.consumer.util.KafkaUtil.WEATHER_TOPIC;

@RequiredArgsConstructor
@Slf4j
@Component
public class WeatherConsumer {

    @KafkaListener(
            topics = WEATHER_TOPIC,
            groupId = "weather-event-group1",
            containerFactory = "kafkaWeatherListenerContainerFactory"
    )
    public void listenHelpRequestTopic(@Payload WeatherMessageKafka weatherMessageKafka) {
        log.info("receive message {}", weatherMessageKafka);
    }
}
