package ru.t1camp.improve.kafka.producer.service.weather.object;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherMessage {

    private Hit hit;

    private long time;

    private double temp;

    private int pressure;

    private int humidity;

    private Wind wind;
}
