package ru.t1debut.itcamp.consent.core.external.client.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) {

        String responseBody;
        try (InputStream responseBodyInputStream = response.getBody()) {
            responseBody = new String(responseBodyInputStream.readAllBytes(), StandardCharsets.UTF_8);
            log.error("error in rest client: {}", responseBody);
            //todo сделать нормальную систему исключений для external клиента, желательно поддерживающую разные тела ошибок
            // но для начала можно сделать специальный ExternalClientException и заполнить его так, чтобы ExceptionResolver
            // смог понять какая ошибка прилетела от внешнего сервиса
            throw new RuntimeException(responseBody);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
