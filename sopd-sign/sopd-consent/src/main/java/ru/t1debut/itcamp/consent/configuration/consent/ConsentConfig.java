package ru.t1debut.itcamp.consent.configuration.consent;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ConsentConfig {

    Consent consent;

    public record Consent(Duration requestDuration, Duration consentDuration) {}
}
