package ru.improve.itcamp.synthetic.human.core.starter.core.executor;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.ExecutorServiceConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RequiredArgsConstructor
@Service
public class TaskExecutorService {

    private final ExecutorServiceConfig executorServiceConfig;

    private ThreadPoolExecutor executor;

    @PostConstruct
    public void initialExecutorService() {
//        executor = new ThreadPoolExecutor(
//                executorServiceConfig.getTaskExecutorThreadPoolSize(),
//                executorServiceConfig.getTaskExecutorThreadPoolSize(),
//                10000,
//                null,
//                new ArrayBlockingQueue<>(
//                        executorServiceConfig.getTaskQueueSize(),
//                        true
//                )
//        );
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    }

    public void addTask(Runnable task) {
        if (executor.getQueue().size() < executorServiceConfig.getTaskQueueSize()) {
            executor.execute(task);
        } else {
            throw new SecurityException();
        }
    }

    @PreDestroy
    public void stopExecutorService() {
        executor.shutdown();
    }
}
