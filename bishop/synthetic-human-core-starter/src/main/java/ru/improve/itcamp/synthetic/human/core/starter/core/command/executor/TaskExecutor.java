package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor;

import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;

public interface TaskExecutor {

    void executeTask(Runnable task);

    CommandPriority getExecutorPriority();
}
