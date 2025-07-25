package ru.improve.itcamp.auth.service.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import ru.improve.itcamp.auth.service.api.dto.user.UserResponse;

import static ru.improve.itcamp.auth.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface UserControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<UserResponse> getUserByAuth();
}
