package ru.t1camp.improve.kafka.producer.service.weather.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.t1camp.improve.kafka.producer.service.weather.WeatherProducerService;
import ru.t1camp.improve.kafka.producer.service.weather.object.WeatherMessage;

import static ru.t1camp.improve.kafka.producer.util.KafkaUtil.WEATHER_TOPIC;

@RequiredArgsConstructor
@Service
public class WeatherProducerServiceImpl implements WeatherProducerService {

    private final KafkaTemplate<String, WeatherMessage> weatherKafkaTemplate;

    @Override
    public void sendMessageInWeatherTopic(WeatherMessage weatherMessage) {
        weatherKafkaTemplate.send(WEATHER_TOPIC, weatherMessage);
    }
}
