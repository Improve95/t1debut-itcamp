package ru.improve.itcamp.auth.service.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import static ru.improve.itcamp.auth.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface AuthControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Void> logout();

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Void> logoutAllTokens();
}
