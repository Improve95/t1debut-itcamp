package ru.t1debut.itcamp.auth.service.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1debut.itcamp.auth.service.api.controller.spec.CheckControllerSpec;
import ru.t1debut.itcamp.auth.service.core.service.EmailService;

import static ru.t1debut.itcamp.auth.service.api.ApiPaths.CHECK;
import static ru.t1debut.itcamp.auth.service.api.ApiPaths.EMAIL;
import static ru.t1debut.itcamp.auth.service.api.ApiPaths.TOKEN;

@RequiredArgsConstructor
@RestController
@RequestMapping(CHECK)
public class CheckController implements CheckControllerSpec {

    private final EmailService emailService;

    @GetMapping(TOKEN)
    public ResponseEntity<Void> tokenIsValid() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(EMAIL)
    public ResponseEntity<Boolean> emailAddressIsExist(@RequestBody @Valid @NotBlank @Email String email) {
        emailService.emailAddressIsExist(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
