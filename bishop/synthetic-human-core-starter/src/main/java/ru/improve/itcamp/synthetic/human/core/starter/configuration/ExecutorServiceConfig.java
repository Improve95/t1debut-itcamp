package ru.improve.itcamp.synthetic.human.core.starter.configuration;

import lombok.Value;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.starter.SyntheticHumanProperties;

@Value
public class ExecutorServiceConfig {

    int taskExecutorThreadPoolSize;

    int taskQueueSize;

    public ExecutorServiceConfig(SyntheticHumanProperties syntheticHumanProperties) {
        this.taskExecutorThreadPoolSize = syntheticHumanProperties.getTaskExecutorThreadPoolSize();
        this.taskQueueSize = syntheticHumanProperties.getTaskQueueSize();
    }
}
