package ru.t1debut.itcamp.consent.api.controller.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static ru.t1debut.itcamp.consent.util.MessageKeys.API_OK;
import static ru.t1debut.itcamp.consent.util.MessageKeys.API_RESPONSE_OK;

@ApiResponse(
        responseCode = API_OK,
        description = API_RESPONSE_OK
)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseOk {
}
