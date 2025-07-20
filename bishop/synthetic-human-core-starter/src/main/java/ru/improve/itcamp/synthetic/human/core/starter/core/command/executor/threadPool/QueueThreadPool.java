package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.executor.ExecutorConfig;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.starter.SyntheticHumanConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
@Component
public class QueueThreadPool {

    private final ExecutorConfig executorConfig;

    private ThreadPoolExecutor executor;

    public QueueThreadPool(SyntheticHumanConfig syntheticHumanConfig) {
        this.executorConfig = syntheticHumanConfig.getExecutor();
    }

    @PostConstruct
    public void initialExecutorService() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(executorConfig.getThreadPoolSize());
    }


    public int getQueueSize() {
        return executor.getQueue().size();
    }


    @PreDestroy
    public void stopExecutorService() {
        executor.shutdown();
    }
}
