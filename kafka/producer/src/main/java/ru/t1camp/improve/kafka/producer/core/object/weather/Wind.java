package ru.t1camp.improve.kafka.producer.core.object.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wind {

    private double speed;

    private int deg;

    private double gust;
}
