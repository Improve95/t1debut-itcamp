package ru.improve.itcamp.bishop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.itcamp.bishop.service.CallMethodService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bishop")
public class Controller {

    private final CallMethodService callMethodService;

    @PostMapping("/weyland")
    public ResponseEntity<String> callWithWeylandMethod(@RequestBody WeylandArgument weylandArgument) {
        String result = callMethodService.callMethodWithWeyland(weylandArgument);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/without-weyland")
    public ResponseEntity<String> callWithoutWeylandMethod(@RequestBody WeylandArgument weylandArgument) {
        String result = callMethodService.callMethodWithoutWeyland(weylandArgument);
        return ResponseEntity.ok(result);
    }
}
