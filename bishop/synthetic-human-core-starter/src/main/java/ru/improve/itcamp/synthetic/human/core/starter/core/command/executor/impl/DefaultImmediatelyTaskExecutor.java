package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.TaskExecutor;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool.ImmediatelyThreadPool;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.object.TaskInfo;

@RequiredArgsConstructor
@Component
public class DefaultImmediatelyTaskExecutor implements TaskExecutor {

    private final ImmediatelyThreadPool immediatelyThreadPool;

    @Override
    public void executeTask(TaskInfo taskInfo) {
        immediatelyThreadPool.getExecutor().execute(taskInfo.getCommand());
    }

    @Override
    public CommandPriority getExecutorPriority() {
        return CommandPriority.CRITICAL;
    }
}
