package ru.t1camp.improve.kafka.consumer.core.object.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherMessageKafka {

    private long time;

    private double temp;

    private String description;

    private double windSpeed;

    private int cloudsAll;
}
