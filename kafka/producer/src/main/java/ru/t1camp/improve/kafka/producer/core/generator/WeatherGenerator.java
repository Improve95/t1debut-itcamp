package ru.t1camp.improve.kafka.producer.core.generator;

import org.springframework.stereotype.Component;
import ru.t1camp.improve.kafka.producer.core.object.weather.Clouds;
import ru.t1camp.improve.kafka.producer.core.object.weather.Weather;
import ru.t1camp.improve.kafka.producer.core.object.weather.WeatherMain;
import ru.t1camp.improve.kafka.producer.core.object.weather.WeatherMessage;
import ru.t1camp.improve.kafka.producer.core.object.weather.Wind;

import java.util.List;
import java.util.Random;

@Component
public class WeatherGenerator {

    private final Random random = new Random();

    public WeatherMessage generateWeatherMessage() {
        return WeatherMessage.builder()
                .weather(List.of(
                        Weather.builder()
                                .id(random.nextInt())
                                .description("not default desc")
                                .main("main")
                                .build()
                ))
                .main(WeatherMain.builder()
                        .temp(random.nextDouble(-10, 20))
                        .build())
                .wind(Wind.builder()
                        .speed(random.nextDouble(0, 10))
                        .build())
                .clouds(Clouds.builder()
                        .all(random.nextInt(10, 100))
                        .build())
                .build();
    }
}
