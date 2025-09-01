package ru.t1debut.itcamp.consent.api.exception;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ErrorResponse {

    private int errorCode;

    private String message;
}
