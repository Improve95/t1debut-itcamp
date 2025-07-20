package ru.improve.itcamp.synthetic.human.core.starter.core.command.object;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskInfo {

    private String author;

    private Runnable command;
}
