package ru.improve.itcamp.synthetic.human.core.starter.core.command.executor;

import org.springframework.stereotype.Component;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class TaskExecutorFactory {

    private final Map<CommandPriority, TaskExecutor> taskExecutorMap;

    public TaskExecutorFactory(List<TaskExecutor> taskExecutors) {
        taskExecutorMap = new HashMap<>(taskExecutors.stream()
                .collect(toMap(TaskExecutor::getExecutorPriority, Function.identity())));
    }

    public TaskExecutor getTaskExecutor(CommandPriority commandPriority) {
        return taskExecutorMap.get(commandPriority);
    }
}
