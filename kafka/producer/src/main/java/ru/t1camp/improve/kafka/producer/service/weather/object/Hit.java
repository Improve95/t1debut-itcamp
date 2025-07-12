package ru.t1camp.improve.kafka.producer.service.weather.object;

import lombok.Data;

@Data
public class Hit {

    private String place;

    private Point point;
}
