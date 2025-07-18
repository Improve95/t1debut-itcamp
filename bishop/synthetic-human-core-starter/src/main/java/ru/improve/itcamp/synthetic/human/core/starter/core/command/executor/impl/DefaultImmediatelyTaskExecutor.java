package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.TaskExecutor;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool.ImmediatelyThreadPool;

@RequiredArgsConstructor
@Service
public class DefaultImmediatelyTaskExecutor implements TaskExecutor {

    private final ImmediatelyThreadPool immediatelyThreadPool;

    @Override
    public void executeTask(Runnable task) {
        immediatelyThreadPool.getExecutor().execute(task);
    }

    @Override
    public CommandPriority getExecutorPriority() {
        return CommandPriority.CRITICAL;
    }
}
