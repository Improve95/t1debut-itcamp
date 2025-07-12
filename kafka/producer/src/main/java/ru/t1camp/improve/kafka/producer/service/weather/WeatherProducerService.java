package ru.t1camp.improve.kafka.producer.service.weather;

import ru.t1camp.improve.kafka.producer.service.weather.object.WeatherMessage;

public interface WeatherProducerService {

    void sendMessageInWeatherTopic(WeatherMessage weatherMessage);
}
