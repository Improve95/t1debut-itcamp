package ru.improve.itcamp.synthetic.human.core.starter.configuration.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "synthetic-human")
public class SyntheticHumanProperties {

    int taskExecutorThreadPoolSize = Runtime.getRuntime().availableProcessors();

    int taskQueueSize = Runtime.getRuntime().availableProcessors() * 2;
}
