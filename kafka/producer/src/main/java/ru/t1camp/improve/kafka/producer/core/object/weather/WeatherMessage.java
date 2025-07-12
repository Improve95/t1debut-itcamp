package ru.t1camp.improve.kafka.producer.core.object.weather;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeatherMessage {

    private List<Weather> weather;

    private WeatherMain main;

    private int visibility;

    private Wind wind;

    private Clouds clouds;

    private long dt;
}
