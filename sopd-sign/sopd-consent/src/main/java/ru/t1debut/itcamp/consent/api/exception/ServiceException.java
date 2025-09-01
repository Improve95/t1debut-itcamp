package ru.t1debut.itcamp.consent.api.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private ErrorCode code;

    private String[] params;

    private Throwable cause;

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode;
    }

    public ServiceException(ErrorCode errorCode, String... params) {
        this.code = errorCode;
        this.params = params;
    }

    public ServiceException(ErrorCode errorCode, Throwable cause) {
        this.code = errorCode;
        this.cause = cause;
    }
}
