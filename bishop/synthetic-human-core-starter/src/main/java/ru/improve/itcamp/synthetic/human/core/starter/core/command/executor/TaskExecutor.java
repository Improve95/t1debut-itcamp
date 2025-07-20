package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor;

import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.object.TaskInfo;

public interface TaskExecutor {

    void executeTask(TaskInfo taskInfo);

    CommandPriority getExecutorPriority();
}
