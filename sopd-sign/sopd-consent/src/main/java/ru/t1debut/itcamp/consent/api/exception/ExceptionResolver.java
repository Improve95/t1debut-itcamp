package ru.t1debut.itcamp.consent.api.exception;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.INVALID_REQUEST;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.SOPD_ERROR;
import static ru.t1debut.itcamp.consent.util.ExceptionUtil.createExceptionMessage;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_ACCESS_DENIED;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_ALREADY_EXIST;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_CANT_SEND_SOPD;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_EXPIRED;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_ILLEGAL_DTO_VALUE;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_ILLEGAL_VALUE;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_INTERNAL_SERVER_ERROR;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_INVALID_REQUEST;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_NOT_FOUND;
import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_UNAUTHORIZED;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ACCESS_DENIED;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ALREADY_EXIST;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.EXPIRED;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ILLEGAL_DTO_VALUE;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.NOT_FOUND;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionResolver {

    private static final ImmutableMap<ErrorCode, String> messageKeyMap = ImmutableMap.of(
            INTERNAL_SERVER_ERROR, TITLE_INTERNAL_SERVER_ERROR,
            ALREADY_EXIST, TITLE_ALREADY_EXIST,
            ILLEGAL_DTO_VALUE, TITLE_ILLEGAL_DTO_VALUE,
            ILLEGAL_VALUE, TITLE_ILLEGAL_VALUE,
            NOT_FOUND, TITLE_NOT_FOUND,
            UNAUTHORIZED, TITLE_UNAUTHORIZED,
            EXPIRED, TITLE_EXPIRED,
            ACCESS_DENIED, TITLE_ACCESS_DENIED,
            SOPD_ERROR, TITLE_CANT_SEND_SOPD,
            INVALID_REQUEST, TITLE_INVALID_REQUEST
    );

    private static final ImmutableMap<ErrorCode, HttpStatus> httpStatusMap = ImmutableMap.of(
            INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
            SOPD_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
            ALREADY_EXIST, HttpStatus.BAD_REQUEST,
            ILLEGAL_VALUE, HttpStatus.BAD_REQUEST,
            ILLEGAL_DTO_VALUE, HttpStatus.BAD_REQUEST,
            INVALID_REQUEST, HttpStatus.BAD_REQUEST,
            EXPIRED, HttpStatus.BAD_REQUEST,
            NOT_FOUND, HttpStatus.NOT_FOUND,
            UNAUTHORIZED, HttpStatus.UNAUTHORIZED,
            ACCESS_DENIED, HttpStatus.FORBIDDEN
    );

    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> resolveHandleException(Exception ex) {
        ErrorInfo errorInfo = resolveException(ex);
        return ResponseEntity.status(httpStatusMap.get(errorInfo.code))
                .body(ErrorResponse.builder()
                        .errorCode(errorInfo.code.getCode())
                        .message(errorInfo.message)
                        .build()
                );
    }

    private ErrorInfo resolveException(Exception ex) {
        if (ex instanceof ServiceException serviceEx) {
            return resolveServiceException(serviceEx);
        } else if (ex instanceof MethodArgumentNotValidException ||
                   ex instanceof HandlerMethodValidationException ||
                   ex instanceof MethodArgumentTypeMismatchException) {
            return resolveDtoException(ex);
        } else if (ex instanceof AuthorizationDeniedException authDeniedException) {
            return resolveAccessDeniedException(authDeniedException);
        }
        return resolveAnotherException(ex);
    }

    private ErrorInfo resolveServiceException(ServiceException ex) {
        ErrorCode code = ex.getCode();
        StringBuilder message = new StringBuilder();
        message.append(resolveMessage(messageKeyMap.get(code), ex.getParams()));

        if (ex.getMessage() != null) {
            message.append(": " + resolveMessage(ex.getMessage(), ex.getParams()));
        }

        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            message.append(", cause " + ex.getCause().getMessage());
        }
        log.error(createExceptionMessage(ex, message.toString()));
        return ErrorInfo.of(code, message.toString());
    }

    private ErrorInfo resolveDtoException(Exception ex) {
        String message = getParametersWithErrorAndMessages(ex).stream()
                .map(parameterMessages ->
                        parameterMessages.parameter + " " + parameterMessages.messages.stream()
                                .map(this::resolveMessage)
                                .collect(Collectors.joining(", ")))
                .collect(Collectors.joining("; "));

        log.error(createExceptionMessage(ex, message));
        return ErrorInfo.of(ILLEGAL_DTO_VALUE, resolveMessage(messageKeyMap.get(ILLEGAL_DTO_VALUE), message));
    }

    private List<ParameterMessages> getParametersWithErrorAndMessages(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException e) {
            return e.getBindingResult().getFieldErrors().stream()
                    .map(fieldError -> ParameterMessages.of(
                            fieldError.getField(),
                            List.of(fieldError.getDefaultMessage())
                    ))
                    .toList();
        } else if (ex instanceof HandlerMethodValidationException e) {
            return e.getAllValidationResults().stream()
                    .map(parameterValidationResult -> ParameterMessages.of(
                            parameterValidationResult.getMethodParameter().getParameterName(),
                            parameterValidationResult.getResolvableErrors().stream()
                                    .map(MessageSourceResolvable::getDefaultMessage)
                                    .toList()
                    ))
                    .toList();
        } else if (ex instanceof MethodArgumentTypeMismatchException e) {
            return List.of(ParameterMessages.of(e.getParameter().getParameterName(), List.of(e.getMessage())));
        }
        return null;
    }

    private ErrorInfo resolveAccessDeniedException(AuthorizationDeniedException ex) {
        String message = resolveMessage(messageKeyMap.get(ACCESS_DENIED), (String) null) + ": " + ex.getMessage();
        log.error(createExceptionMessage(ex, message));
        return ErrorInfo.of(ACCESS_DENIED, message);
    }

    private ErrorInfo resolveAnotherException(Exception ex) {
        log.error(createExceptionMessage(ex, null));
        String logErrorMessage = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        return ErrorInfo.of(
                INTERNAL_SERVER_ERROR,
                resolveMessage(messageKeyMap.get(INTERNAL_SERVER_ERROR), (String) null) + ": " + logErrorMessage
        );
    }
    
    private record ErrorInfo(ErrorCode code, String message) {
        public static ErrorInfo of(ErrorCode code, String message) {
            return new ErrorInfo(code, message);
        }
    }

    private record ParameterMessages(String parameter, List<String> messages) {
        public static ParameterMessages of(String parameter, List<String> messages) {
            return new ParameterMessages(parameter, messages);
        }
    }

    public String resolveMessage(String key, String... params) {
        return messageSource.getMessage(key, params, key, LocaleContextHolder.getLocale());
    }
}
