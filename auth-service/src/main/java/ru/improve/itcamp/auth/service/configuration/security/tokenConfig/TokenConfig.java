package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "app.token", ignoreUnknownFields = false)
public class TokenConfig {

    AccessTokenConfig access;

    public record AccessTokenConfig(String secret, Duration duration) {}
}
