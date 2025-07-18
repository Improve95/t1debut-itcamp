package ru.improve.itcamp.synthetic.human.core.starter.core.command;

import ru.improve.itcamp.synthetic.human.core.starter.api.dto.command.CommandRequest;

public interface TaskSubmitter {

    void executeTask(CommandRequest commandRequest);
}
