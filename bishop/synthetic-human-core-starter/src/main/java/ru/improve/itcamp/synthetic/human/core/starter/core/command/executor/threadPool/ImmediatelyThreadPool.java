package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Getter
@Component
public class ImmediatelyThreadPool extends AbstractThreadPool {

    @PostConstruct
    public void initialExecutorService() {
        this.executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    @PreDestroy
    public void stopExecutorService() {
        shutdownThreadPool();
    }
}
