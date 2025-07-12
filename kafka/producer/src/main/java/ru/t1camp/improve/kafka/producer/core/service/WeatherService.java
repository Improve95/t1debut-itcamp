package ru.t1camp.improve.kafka.producer.core.service;

import ru.t1camp.improve.kafka.producer.core.object.weather.WeatherMessage;

public interface WeatherService {

    void sendWeatherIntoBroker(WeatherMessage weatherMessage);
}
