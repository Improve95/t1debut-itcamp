package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
@Component
public class ImmediatelyThreadPool {

    private ThreadPoolExecutor executor;

    @PostConstruct
    public void initialExecutorService() {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    @PreDestroy
    public void stopExecutorService() {
        executor.shutdown();
    }
}
