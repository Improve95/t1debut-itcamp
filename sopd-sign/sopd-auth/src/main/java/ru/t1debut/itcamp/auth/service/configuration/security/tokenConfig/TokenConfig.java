package ru.t1debut.itcamp.auth.service.configuration.security.tokenConfig;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "app.token", ignoreUnknownFields = false)
public class TokenConfig {

    JwsTokenConfig jws;

    public record JwsTokenConfig(String publicKeyPath, String privateKeyPath, Duration duration) {}
}
