package ru.improve.itcamp.synthetic.human.core.starter.configuration.starter;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "synthetic-human")
public class SyntheticHumanProperties {

    int taskExecutorThreadPoolSize;

    int taskQueueSize;
}
