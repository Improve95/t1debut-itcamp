package ru.t1camp.improve.kafka.producer.core.object.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {

    private int id;

    private String main;

    private String description;
}
