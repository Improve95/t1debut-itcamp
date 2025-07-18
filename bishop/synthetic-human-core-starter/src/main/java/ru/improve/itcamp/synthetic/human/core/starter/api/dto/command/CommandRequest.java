package ru.improve.itcamp.synthetic.human.core.starter.api.dto.command;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.CommandPriority;

import java.time.LocalDateTime;

@Data
@Builder
public class CommandRequest {

    @Size(min = 5, max = 1000)
    private String description;

    private CommandPriority priority;

    @Size(min = 5, max = 100)
    private String author;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;
}
