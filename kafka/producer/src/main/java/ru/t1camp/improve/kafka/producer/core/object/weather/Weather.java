package ru.t1camp.improve.kafka.producer.core.object.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    private int id;

    private String main;

    private String description;
}
