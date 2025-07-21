package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class AbstractThreadPool {

    protected ThreadPoolExecutor executor;

    protected void shutdownThreadPool() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    log.error("Executor did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("{} shutdowned", executor);
    }
}
