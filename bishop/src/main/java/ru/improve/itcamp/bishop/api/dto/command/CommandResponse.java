package ru.improve.itcamp.bishop.api.dto.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandResponse {

    private String status;
}
