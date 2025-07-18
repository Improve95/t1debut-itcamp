package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.impl;

import org.springframework.stereotype.Service;
import ru.improve.itcamp.synthetic.human.core.starter.api.exception.ServiceException;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.TaskExecutor;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool.QueueThreadPool;

import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Service
public class DefaultQueueTaskExecutor implements TaskExecutor {

    private final QueueThreadPool threadPool;

    public DefaultQueueTaskExecutor(QueueThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void executeTask(Runnable task) {
        if (threadPool.getExecutor().getTaskCount() < threadPool.getTaskQueueSize()) {
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
