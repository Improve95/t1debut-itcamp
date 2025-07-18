package ru.improve.itcamp.synthetic.human.core.starter.configuration.executor;

import lombok.Value;

@Value
public class ExecutorConfig {

    int threadPoolSize;

    int queueSize;
}
