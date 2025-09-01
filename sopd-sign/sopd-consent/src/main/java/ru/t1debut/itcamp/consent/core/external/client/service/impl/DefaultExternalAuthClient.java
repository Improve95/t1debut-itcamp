package ru.t1debut.itcamp.consent.core.external.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import ru.t1debut.itcamp.consent.api.exception.ServiceException;
import ru.t1debut.itcamp.consent.core.external.client.service.ExternalAuthClient;

import java.time.Duration;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.t1debut.itcamp.consent.core.external.client.api.ExternalApiPath.CHECK;
import static ru.t1debut.itcamp.consent.core.external.client.api.ExternalApiPath.EMAIL;

//todo вынести в отдельный репозиторий

@Slf4j
public class DefaultExternalAuthClient extends AbstractExternalClient implements ExternalAuthClient {

    public DefaultExternalAuthClient(String authServerUrl, Duration connectTimeout, Duration readTimeout) {
        super(authServerUrl, connectTimeout, readTimeout);
    }

    @Override
    public boolean checkAuthorizationRequest(String authorization) {
        /*HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, "Bearer " + authorization);
        try {
            restTemplate.exchange(
                    externalServerUrl + CHECK + TOKEN,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Void.class
            );
        } catch (RestClientException ex) {
            log.error("error in rest client: ", ex);
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }
        return true;*/
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean emailExistRequest(String email, String authorization) {
        log.info("external request email exist: {}", email);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(
                    email,
                    headers
            );
            restTemplate.postForEntity(
                    externalServerUrl + CHECK + EMAIL,
                    httpEntity,
                    Void.class
            );
        } catch (RestClientException ex) {
            log.error("error in rest client: ", ex);
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }
        return true;
    }
}
