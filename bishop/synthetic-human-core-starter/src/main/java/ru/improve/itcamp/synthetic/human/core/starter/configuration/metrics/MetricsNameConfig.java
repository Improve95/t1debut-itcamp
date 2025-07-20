package ru.improve.itcamp.synthetic.human.core.starter.configuration.metrics;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MetricsNameConfig {

    @Value("${QUEUE_TASK_NUMBER_METER_NAME}")
    private String queueTaskNumberMeterName;

    @Value("${COUNTER_REQUEST_BY_NAME_METER_NAME}")
    private String counterRequestByNameCounter;
}
