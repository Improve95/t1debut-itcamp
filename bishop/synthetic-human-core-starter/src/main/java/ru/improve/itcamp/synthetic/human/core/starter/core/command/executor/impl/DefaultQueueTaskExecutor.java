package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.synthetic.human.core.starter.api.exception.ServiceException;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.executor.ExecutorConfig;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.starter.SyntheticHumanConfig;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.TaskExecutor;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool.QueueThreadPool;

import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.ILLEGAL_VALUE;

@Slf4j
@Service
public class DefaultQueueTaskExecutor implements TaskExecutor {

    private final QueueThreadPool threadPool;

    private final ExecutorConfig executorConfig;

    public DefaultQueueTaskExecutor(QueueThreadPool threadPool, SyntheticHumanConfig syntheticHumanConfig) {
        this.threadPool = threadPool;
        this.executorConfig = syntheticHumanConfig.getExecutor();
    }

    @Override
    public void executeTask(Runnable task) {
        var queue = threadPool.getExecutor().getQueue();
        if (queue.size() >= executorConfig.getQueueSize()) {
            throw new ServiceException(ILLEGAL_VALUE, "queue thread is full");
        } else {
            synchronized (this) {
                if (threadPool.getExecutor().getActiveCount() < executorConfig.getQueueSize()) {
                    threadPool.getExecutor().execute(task);
                } else {
                    throw new ServiceException(ILLEGAL_VALUE, "queue thread is full");
                }
            }
        }
    }

    @Override
    public CommandPriority getExecutorPriority() {
        return CommandPriority.COMMON;
    }
}
