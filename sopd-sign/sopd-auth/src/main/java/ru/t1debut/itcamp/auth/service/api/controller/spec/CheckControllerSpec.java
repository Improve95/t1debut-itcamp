package ru.t1debut.itcamp.auth.service.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import static ru.t1debut.itcamp.auth.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface CheckControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Void> tokenIsValid();

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Boolean> emailAddressIsExist(@RequestBody @Valid @NotBlank @Email String email);
}
