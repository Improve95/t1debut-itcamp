package ru.improve.itcamp.synthetic.human.core.starter.api.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.ACCESS_DENIED;
import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.ALREADY_EXIST;
import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.EXPIRED;
import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.ILLEGAL_DTO_VALUE;
import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.NOT_FOUND;
import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.UNAUTHORIZED;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionResolver {

    /*private static Map<ErrorCode, String> messageKeyMap = Map.of(
            INTERNAL_SERVER_ERROR, TITLE_INTERNAL_SERVER_ERROR,
            ALREADY_EXIST, TITLE_ALREADY_EXIST,
            ILLEGAL_DTO_VALUE, TITLE_ILLEGAL_DTO_VALUE,
            ILLEGAL_VALUE, TITLE_ILLEGAL_VALUE,
            NOT_FOUND, TITLE_NOT_FOUND,
            UNAUTHORIZED, TITLE_UNAUTHORIZED,
            EXPIRED, TITLE_EXPIRED,
            ACCESS_DENIED, TITLE_ACCESS_DENIED
    );*/

    private static Map<ErrorCode, HttpStatus> httpStatusMap = Map.of(
            INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
            ALREADY_EXIST, HttpStatus.BAD_REQUEST,
            ILLEGAL_VALUE, HttpStatus.BAD_REQUEST,
            ILLEGAL_DTO_VALUE, HttpStatus.BAD_REQUEST,
            EXPIRED, HttpStatus.BAD_REQUEST,
            NOT_FOUND, HttpStatus.NOT_FOUND,
            UNAUTHORIZED, HttpStatus.UNAUTHORIZED,
            ACCESS_DENIED, HttpStatus.UNAUTHORIZED
    );

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> resolveHandleException(Exception ex) {
        ErrorCodeMessagePair errorCode = resolveException(ex);
        return ResponseEntity.status(httpStatusMap.get(errorCode.code))
                .body(ErrorResponse.builder()
                        .errorCode(errorCode.code.getCode())
                        .message(errorCode.message)
                        .build()
                );
    }

    private ErrorCodeMessagePair resolveException(Exception ex) {
        if (ex instanceof ServiceException serviceEx) {
            return resolveServiceException(serviceEx);
        } else if (ex instanceof MethodArgumentNotValidException ||
                ex instanceof HandlerMethodValidationException) {
            return resolveDtoException(ex);
        }
        return ErrorCodeMessagePair.of(
                INTERNAL_SERVER_ERROR, ex.getMessage()
//                messageUtil.resolveMessage(messageKeyMap.get(INTERNAL_SERVER_ERROR), null)
        );
    }

    private ErrorCodeMessagePair resolveServiceException(ServiceException ex) {
        ErrorCode code = ex.getCode();
        StringBuilder message = new StringBuilder();
//        message.append(messageUtil.resolveMessage(messageKeyMap.get(code), ex.getParams()));

        if (ex.getMessage() != null) {
            message.append(": " + ex.getMessage());
        }

        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            message.append(", cause " + ex.getCause().getMessage());
        }
        return ErrorCodeMessagePair.of(code, message.toString());
    }

    private ErrorCodeMessagePair resolveDtoException(Exception ex) {
        String message = getParametersWithErrorAndMessages(ex).stream()
                .map(parameterMessages -> parameterMessages.parameter + " "
                        + parameterMessages.messages.stream().collect(Collectors.joining(", ")))
                .collect(Collectors.joining("; "));
        return ErrorCodeMessagePair.of(
                ILLEGAL_DTO_VALUE, message
//                messageUtil.resolveMessage(messageKeyMap.get(ILLEGAL_DTO_VALUE), message)
        );
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
//            return e.getAllValidationResults().stream()
//                    .map(parameterValidationResult -> ParameterMessages.of(
//                            parameterValidationResult.getMethodParameter().getParameterName(),
//                            parameterValidationResult.getResolvableErrors().stream()
//                                    .map(MessageSourceResolvable::getDefaultMessage)
//                                    .toList()
//                    ))
//                    .toList();
        }
        return null;
    }

   /* private ErrorCodeMessagePair resolveAccessDeniedException(AuthorizationDeniedException ex) {
        String message = messageUtil.resolveMessage(messageKeyMap.get(UNAUTHORIZED), null) + ": " + ex.getMessage();
        return ErrorCodeMessagePair.of(
                UNAUTHORIZED,
                message
        );
    }*/

//    public String resolveMessage(String key, String... params) {
//        return messageSource.getMessage(key, params, key, LocaleContextHolder.getLocale());
//    }

    private record ErrorCodeMessagePair(ErrorCode code, String message) {
        public static ErrorCodeMessagePair of(ErrorCode code, String message) {
            return new ErrorCodeMessagePair(code, message);
        }
    }

    private record ParameterMessages(String parameter, List<String> messages) {
        public static ParameterMessages of(String parameter, List<String> messages) {
            return new ParameterMessages(parameter, messages);
        }
    }
}
