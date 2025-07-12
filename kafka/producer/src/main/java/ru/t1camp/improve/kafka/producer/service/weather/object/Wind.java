package ru.t1camp.improve.kafka.producer.service.weather.object;

import lombok.Data;

@Data
public class Wind {

    private double speed;

    private int deg;
}
