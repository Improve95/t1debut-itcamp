package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.impl;

import org.springframework.stereotype.Service;
import ru.improve.itcamp.synthetic.human.core.starter.api.exception.ServiceException;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.executor.ExecutorConfig;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.starter.SyntheticHumanConfig;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.TaskExecutor;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool.QueueThreadPool;

import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

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
        if (threadPool.getExecutor().getTaskCount() < executorConfig.getQueueSize()) {
            threadPool.getExecutor().execute(task);
        } else {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommandPriority getExecutorPriority() {
        return CommandPriority.COMMON;
    }
}
