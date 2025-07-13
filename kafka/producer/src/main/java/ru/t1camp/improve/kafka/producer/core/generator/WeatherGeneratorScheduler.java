package ru.t1camp.improve.kafka.producer.core.generator;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.t1camp.improve.kafka.producer.core.object.weather.WeatherMessage;
import ru.t1camp.improve.kafka.producer.core.service.WeatherService;

import java.util.Timer;
import java.util.TimerTask;

@RequiredArgsConstructor
@Slf4j
public class WeatherGeneratorScheduler {

    private final WeatherGenerator weatherGenerator;

    private final WeatherService weatherService;

    private final long period;

    private static Timer timer = new Timer();

    @PostConstruct
    public void startTimerWeatherGeneration() {
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        WeatherMessage weatherMessage = weatherGenerator.generateWeatherMessage();
                        weatherService.sendWeatherIntoBroker(weatherMessage);
                        log.info("new message {}", weatherMessage);
                    }
                },
                0,
                period
        );
    }

    @PreDestroy
    public void stopTimerWeatherGeneration() {
        timer.cancel();
    }
}
