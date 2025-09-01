package ru.t1debut.itcamp.consent.core.external.client.service.impl;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import ru.t1debut.itcamp.consent.core.external.client.api.exception.RestTemplateErrorHandler;

import java.time.Duration;

public class AbstractExternalClient {

    protected final String externalServerUrl;

    protected final RestTemplate restTemplate;

    protected static final String AUTHORIZATION_HEADER = "Authorization";

    public AbstractExternalClient(String externalServerUrl, Duration connectTimeout, Duration readTimeout) {
        if (externalServerUrl == null || externalServerUrl.isBlank()) {
            throw new IllegalArgumentException("External service URL cannot be null or blank");
        }
        if (connectTimeout == null || connectTimeout.isNegative() || connectTimeout.isZero()) {
            throw new IllegalArgumentException("Connect timeout cannot be null or negative");
        }
        if (readTimeout == null || readTimeout.isNegative() || readTimeout.isZero()) {
            throw new IllegalArgumentException("Read timeout cannot be null or negative");
        }

        this.externalServerUrl = externalServerUrl;
        this.restTemplate = new RestTemplateBuilder()
                .errorHandler(new RestTemplateErrorHandler())
                .setConnectTimeout(connectTimeout)
                .setReadTimeout(readTimeout)
                .build();
    }
}
