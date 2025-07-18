package ru.improve.itcamp.synthetic.human.core.starter.core.command.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.synthetic.human.core.starter.api.dto.command.CommandRequest;
import ru.improve.itcamp.synthetic.human.core.starter.api.exception.ServiceException;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.TaskSubmitter;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.TaskExecutor;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.TaskExecutorFactory;

import java.util.Random;

import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultTaskSubmitter implements TaskSubmitter {

    private final TaskExecutorFactory taskExecutorFactory;

    @Override
    public void executeTask(CommandRequest commandRequest) {
        TaskExecutor taskExecutor = taskExecutorFactory.getTaskExecutor(commandRequest.getPriority());
        taskExecutor.executeTask(() -> {
            log.info("command start: {}", commandRequest);
            try {
                Thread.sleep(new Random().nextInt(1000, 5000));
            } catch (InterruptedException ex) {
                throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
            }
            log.info("command end: {}", commandRequest);
        });
    }
}
