package ru.improve.itcamp.synthetic.human.core.starter.core.executor;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TaskExecutorService {

    @Value("${task-executor-threadPool-size}")
    private int threadPoolSize;

    private ExecutorService executor;

    @PostConstruct
    public void initialExecutorService() {
        executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    @PreDestroy
    public void stopExecutorService() {
        executor.shutdown();
    }
}
