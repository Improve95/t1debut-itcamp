package ru.t1debut.itcamp.consent.configuration.api;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

@Configuration
@SecurityScheme(
        name = SWAGGER_SECURITY_SCHEME_NAME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "RSA-JWS",
        scheme = "bearer"
)
public class OpenApiConfig {}
