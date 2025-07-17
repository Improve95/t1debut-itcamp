package ru.improve.itcamp.synthetic.human.core.starter.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.itcamp.synthetic.human.core.starter.api.dto.command.CommandRequest;
import ru.improve.itcamp.synthetic.human.core.starter.api.dto.command.CommandResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/commands")
public class CommandController {

    @PostMapping("/request")
    public ResponseEntity<CommandResponse> requestCommand(@RequestBody CommandRequest commandRequest) {
        return null;
    }
}
