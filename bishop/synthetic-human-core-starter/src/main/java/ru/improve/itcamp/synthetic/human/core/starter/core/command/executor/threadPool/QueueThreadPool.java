package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.ExecutorServiceConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
@Component
public class QueueThreadPool {

    private final ExecutorServiceConfig executorServiceConfig;

    private final int taskExecutorThreadPoolSize;

    private final int taskQueueSize;

    private ThreadPoolExecutor executor;

    public QueueThreadPool(ExecutorServiceConfig executorServiceConfig) {
        this.executorServiceConfig = executorServiceConfig;
        this.taskExecutorThreadPoolSize = executorServiceConfig.getTaskExecutorThreadPoolSize();
        this.taskQueueSize = executorServiceConfig.getTaskQueueSize();
    }

    @PostConstruct
    public void initialExecutorService() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                executorServiceConfig.getTaskExecutorThreadPoolSize()
        );
    }

    @PreDestroy
    public void stopExecutorService() {
        executor.shutdown();
    }
}
