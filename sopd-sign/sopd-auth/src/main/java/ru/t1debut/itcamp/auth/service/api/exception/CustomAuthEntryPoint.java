package ru.t1debut.itcamp.auth.service.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.UNAUTHORIZED;

@RequiredArgsConstructor
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {

        ServiceException serviceException = new ServiceException(UNAUTHORIZED, authException);
        handlerExceptionResolver.resolveException(request, response, null, serviceException);
    }
}
