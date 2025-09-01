package ru.t1debut.itcamp.consent.api.controller.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.t1debut.itcamp.consent.api.exception.ErrorResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.t1debut.itcamp.consent.util.MessageKeys.API_BAD_REQUEST;
import static ru.t1debut.itcamp.consent.util.MessageKeys.API_RESPONSE_ALREADY_EXIST;

@ApiResponse(
        responseCode = API_BAD_REQUEST,
        description = API_RESPONSE_ALREADY_EXIST,
        content = {
                @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        }
)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseAlreadyExist {
}
