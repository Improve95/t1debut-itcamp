package ru.improve.itcamp.synthetic.human.core.starter.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.itcamp.synthetic.human.core.starter.api.dto.command.CommandRequest;
import ru.improve.itcamp.synthetic.human.core.starter.api.dto.command.CommandResponse;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.TaskSubmitter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/commands")
public class CommandController {

    private final TaskSubmitter taskSubmitter;

    @PostMapping("/request")
    public ResponseEntity<CommandResponse> requestCommand(@RequestBody @Valid CommandRequest commandRequest) {
        taskSubmitter.executeTask(commandRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
