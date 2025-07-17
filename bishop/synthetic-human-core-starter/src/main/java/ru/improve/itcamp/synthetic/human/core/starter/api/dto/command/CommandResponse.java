package ru.improve.itcamp.synthetic.human.core.starter.api.dto.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandResponse {

    private String status;
}
