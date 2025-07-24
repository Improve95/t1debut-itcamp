package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "app.token", ignoreUnknownFields = false)
public class TokenConfig {

    AccessTokenConfig access;

    RefreshTokenConfig refresh;

    public record AccessTokenConfig(String secret, Duration duration) {}

    public record RefreshTokenConfig(String secret, Duration duration) {}
}
