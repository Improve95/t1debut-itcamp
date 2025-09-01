package ru.t1debut.itcamp.consent.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        ServiceException serviceException = new ServiceException(UNAUTHORIZED);
        handlerExceptionResolver.resolveException(request, response, null, serviceException);
    }
}
