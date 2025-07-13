package ru.t1camp.improve.kafka.producer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1camp.improve.kafka.producer.core.generator.WeatherGenerator;
import ru.t1camp.improve.kafka.producer.core.generator.WeatherGeneratorScheduler;
import ru.t1camp.improve.kafka.producer.core.service.WeatherService;

@Configuration
public class WeatherGeneratorConfiguration {

    @Value("${weather.generator.period}")
    private long period;

    @Bean
    public WeatherGeneratorScheduler weatherGeneratorScheduler(
            WeatherGenerator weatherGenerator,
            WeatherService weatherService
    ) {
        return new WeatherGeneratorScheduler(weatherGenerator, weatherService, period);
    }
}
