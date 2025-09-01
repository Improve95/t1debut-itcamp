package ru.t1debut.itcamp.consent.configuration.security.tokenConfig;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "security.token", ignoreUnknownFields = false)
public class TokenConfig {

    JwsTokenConfig jws;

    public record JwsTokenConfig(String publicKeyPath) {}
}
