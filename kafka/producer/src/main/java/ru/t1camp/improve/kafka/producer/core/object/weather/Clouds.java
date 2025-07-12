package ru.t1camp.improve.kafka.producer.core.object.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Clouds {

    private int all;
}
