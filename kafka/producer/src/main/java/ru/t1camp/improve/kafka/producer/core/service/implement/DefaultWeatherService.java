package ru.t1camp.improve.kafka.producer.core.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1camp.improve.kafka.producer.core.object.kafka.WeatherMessageKafka;
import ru.t1camp.improve.kafka.producer.core.object.weather.WeatherMessage;
import ru.t1camp.improve.kafka.producer.core.producer.WeatherProducer;
import ru.t1camp.improve.kafka.producer.core.service.WeatherService;

import java.security.DrbgParameters;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class DefaultWeatherService implements WeatherService {

    private final WeatherProducer weatherProducer;

    public void sendWeatherIntoBroker(WeatherMessage weatherMessage) {
        weatherProducer.sendMessageInWeatherTopic(WeatherMessageKafka.builder()
                .time(Instant.now().toEpochMilli())
                .temp()
                .description()
                .windSpeed()
                .cloudsAll()
                .build());
    }
}
