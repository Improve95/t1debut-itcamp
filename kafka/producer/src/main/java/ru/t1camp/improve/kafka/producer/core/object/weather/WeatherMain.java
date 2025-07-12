package ru.t1camp.improve.kafka.producer.core.object.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherMain {

    private double temp;

    private double feels_like;

    private int pressure;

    private double temp_min;

    private double temp_max;

    private int humidity;
}
