package ru.t1camp.improve.kafka.producer.core.object.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherMain {

    private double temp;

    private double feels_like;

    private int pressure;

    private double temp_min;

    private double temp_max;

    private int humidity;
}
