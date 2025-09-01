package ru.t1debut.itcamp.consent.configuration.external;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "external", ignoreUnknownFields = false)
public class ExternalClientDatasource {

    AuthClient auth;

    FrontendConfig frontend;

    public record AuthClient(String url, Duration connectTimeout, Duration readTimeout) {}

    public record FrontendConfig(String url) {}
}
