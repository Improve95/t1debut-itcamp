package ru.t1debut.itcamp.consent.core.security.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.api.exception.ServiceException;
import ru.t1debut.itcamp.consent.core.security.object.JwtToken;
import ru.t1debut.itcamp.consent.core.security.object.UserClaim;
import ru.t1debut.itcamp.consent.core.security.object.UserSessionDetails;
import ru.t1debut.itcamp.consent.core.security.service.AuthService;
import ru.t1debut.itcamp.consent.core.security.service.TokenCryptoService;
import ru.t1debut.itcamp.consent.util.ExceptionUtil;
import ru.t1debut.itcamp.consent.util.SecurityUtil;

import java.util.List;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.EXPIRED;
import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.UNAUTHORIZED;
import static ru.t1debut.itcamp.consent.util.SecurityUtil.AUTHORITIES_CLAIM;
import static ru.t1debut.itcamp.consent.util.SecurityUtil.USER_CLAIM;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

    private final TokenCryptoService tokenCryptoService;

    @Override
    public boolean setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info(
                "try authorize request: {}, header: {}",
                request.getRequestURI(),
                request.getHeader(HttpHeaders.AUTHORIZATION)
        );
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken)) {
            log.info("request without auth jwt token: {}", request.getRequestURI());
            return true;
        }

        try {
            JwtToken token = (JwtToken) auth.getPrincipal();
            int userId = Integer.parseInt(token.getSubject());

            if (tokenCryptoService.tokenIsExpired(token)) {
                throw new ServiceException(EXPIRED);
            }

//            if (!accessTokenService.tokenIsPresent(token.getToken())) {
//                throw new ServiceException(UNAUTHORIZED);
//            }

            List<String> roles = token.getClaim(AUTHORITIES_CLAIM);
            UsernamePasswordAuthenticationToken userPasswordAuth =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            roles.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .toList()
                    );

            UserClaim userClaim = SecurityUtil.getClaimObjectFromToken(token, USER_CLAIM, UserClaim.class);
            userPasswordAuth.setDetails(new UserSessionDetails(userClaim, token.getToken()));
            securityContext.setAuthentication(userPasswordAuth);

            return true;
        } catch (Exception ex) {
            securityContext.setAuthentication(null);
            SecurityContextHolder.clearContext();
            response.reset();
            log.error("request unauthorized: {}", ExceptionUtil.createExceptionMessage(ex, ex.getLocalizedMessage()));
            if (ex instanceof ServiceException e && e.getCode() == EXPIRED) {
                throw e;
            }
            throw new ServiceException(UNAUTHORIZED);
        }
    }
}
