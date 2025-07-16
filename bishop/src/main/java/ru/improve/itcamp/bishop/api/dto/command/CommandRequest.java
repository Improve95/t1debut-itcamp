package ru.improve.itcamp.bishop.api.dto.command;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.improve.itcamp.bishop.core.command.CommandPriority;

import java.time.LocalDateTime;

@Data
@Builder
public class CommandRequest {

    @Size(max = 1000)
    private String description;

    private CommandPriority priority;

    @Size(max = 100)
    private String author;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;
}
