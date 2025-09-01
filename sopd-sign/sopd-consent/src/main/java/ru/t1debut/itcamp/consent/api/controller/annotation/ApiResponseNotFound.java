package ru.t1debut.itcamp.consent.api.controller.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.t1debut.itcamp.consent.util.MessageKeys.API_NOT_FOUND;
import static ru.t1debut.itcamp.consent.util.MessageKeys.API_RESPONSE_NOT_FOUND;

@ApiResponse(
        responseCode = API_NOT_FOUND,
        description = API_RESPONSE_NOT_FOUND,
        content = {
                @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ru.t1debut.itcamp.consent.api.exception.ErrorResponse.class)
                )
        }
)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseNotFound {
}
