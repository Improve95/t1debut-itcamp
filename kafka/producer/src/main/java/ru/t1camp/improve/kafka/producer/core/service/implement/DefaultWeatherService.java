package ru.t1camp.improve.kafka.producer.core.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1camp.improve.kafka.producer.core.object.kafka.WeatherMessageKafka;
import ru.t1camp.improve.kafka.producer.core.object.location.Point;
import ru.t1camp.improve.kafka.producer.core.object.weather.Weather;
import ru.t1camp.improve.kafka.producer.core.object.weather.WeatherMessage;
import ru.t1camp.improve.kafka.producer.core.producer.WeatherProducer;
import ru.t1camp.improve.kafka.producer.core.service.WeatherService;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultWeatherService implements WeatherService {

    private final WeatherProducer weatherProducer;

    private void getWeatherInfo(Point point) {
        //todo find real weather by Point(lat, lng) on map
    }

    @Override
    public void sendWeatherIntoBroker(WeatherMessage weatherMessage) {
        weatherProducer.sendMessageInWeatherTopic(
                WeatherMessageKafka.builder()
                        .time(Instant.now().toEpochMilli())
                        .temp(weatherMessage.getMain().getTemp())
                        .description(
                                Optional.of(weatherMessage.getWeather().get(0))
                                        .orElse(Weather.builder().description("default desc").build())
                                        .getDescription()
                        )
                        .windSpeed(weatherMessage.getWind().getSpeed())
                        .cloudsAll(weatherMessage.getClouds().getAll())
                        .build()
        );
    }
}
