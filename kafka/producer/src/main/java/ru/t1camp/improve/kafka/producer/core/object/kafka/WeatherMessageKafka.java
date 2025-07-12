package ru.t1camp.improve.kafka.producer.core.object.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherMessageKafka {

    private long time;

    private double temp;

    private String description;

    private double windSpeed;

    private int cloudsAll;
}
