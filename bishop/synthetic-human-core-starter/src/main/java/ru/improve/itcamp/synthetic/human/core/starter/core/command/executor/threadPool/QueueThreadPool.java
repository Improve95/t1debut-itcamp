package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.executor.ExecutorConfig;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.starter.SyntheticHumanConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class QueueThreadPool extends AbstractThreadPool {

    private final ExecutorConfig executorConfig;


    public QueueThreadPool(SyntheticHumanConfig syntheticHumanConfig) {
        this.executorConfig = syntheticHumanConfig.getExecutor();
    }

    @PostConstruct
    public void initialExecutorService() {
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(executorConfig.getThreadPoolSize());
    }


    public int getQueueSize() {
        return executor.getQueue().size();
    }


    @PreDestroy
    public void stopExecutorService() {
        shutdownThreadPool();
    }
}
