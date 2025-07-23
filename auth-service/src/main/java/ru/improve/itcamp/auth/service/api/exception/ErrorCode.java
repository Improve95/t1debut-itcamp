package ru.improve.itcamp.auth.service.api.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(1),

    ALREADY_EXIST(2),

    ILLEGAL_DTO_VALUE(3),

    ILLEGAL_VALUE(4),

    NOT_FOUND(5),

    UNAUTHORIZED(6),

    EXPIRED(7),

    ACCESS_DENIED(8);

    private final int code;
}
