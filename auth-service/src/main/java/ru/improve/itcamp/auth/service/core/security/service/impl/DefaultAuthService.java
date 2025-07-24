package ru.improve.itcamp.auth.service.core.security.service.impl;

import com.nimbusds.jose.crypto.RSAEncrypter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInResponse;
import ru.improve.itcamp.auth.service.api.exception.ServiceException;
import ru.improve.itcamp.auth.service.configuration.security.tokenConfig.TokenConfig;
import ru.improve.itcamp.auth.service.core.security.JwtJweToken;
import ru.improve.itcamp.auth.service.core.security.service.AuthService;
import ru.improve.itcamp.auth.service.core.security.service.TokenCryptoService;
import ru.improve.itcamp.auth.service.core.service.RoleService;
import ru.improve.itcamp.auth.service.core.service.TokenService;
import ru.improve.itcamp.auth.service.core.service.UserService;
import ru.improve.itcamp.auth.service.model.AccessToken;
import ru.improve.itcamp.auth.service.model.User;
import ru.improve.itcamp.auth.service.util.SecurityUtil;

import java.util.List;
import java.util.Set;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.EXPIRED;
import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.UNAUTHORIZED;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.AUTHORITIES_CLAIM;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

    private final UserService userService;

    private final RoleService roleService;

    private final TokenService tokenService;

    private final TokenService accessTokenService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    private final TokenCryptoService tokenCryptoService;

    private final TokenConfig tokenConfig;

    private final RSAEncrypter rsaEncrypter;

    @Override
    public boolean setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken)) {
            return true;
        }

        try {
            JwtJweToken token = (JwtJweToken) auth.getPrincipal();
            int userId = Integer.parseInt(token.getSubject());

            if (tokenCryptoService.tokenIsExpired(token)) {
                throw new ServiceException(EXPIRED);
            }

            if (!tokenService.tokenIsPresent(token.getTokenValue(), userId)) {
                throw new ServiceException(UNAUTHORIZED);
            }

            List<String> roles = token.getClaim(AUTHORITIES_CLAIM);
            securityContext.setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            token.getJweToken(),
                            roles.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .toList()
                    )
            );

            return true;
        } catch (Exception ex) {
            securityContext.setAuthentication(null);
            SecurityContextHolder.clearContext();
            response.reset();
            if (ex instanceof ServiceException e) {
                throw e.getCode() == EXPIRED ? e : new ServiceException(UNAUTHORIZED);
            }
            throw new ServiceException(UNAUTHORIZED);
        }
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = User.builder()
                .email(signInRequest.getEmail())
                .password(passwordEncoder.encode(signInRequest.getPassword()))
                .roles(Set.of(
                        roleService.getRole(SecurityUtil.CLIENT_ROLE)
                ))
                .name(signInRequest.getName())
                .build();

        user = userService.saveUser(user);
        LoginResponse loginResponse = login(
                LoginRequest.builder()
                        .login(signInRequest.getEmail())
                        .password(signInRequest.getPassword())
                        .build()
        );
        return SignInResponse.builder()
                .id(user.getId())
                .accessToken(loginResponse.getAccessToken())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());

        Authentication authentication;
        try {
            authentication = authManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException ex) {
            throw new ServiceException(UNAUTHORIZED);
        }

        User user = (User) authentication.getPrincipal();
        if (user == null) {
            throw new ServiceException(UNAUTHORIZED);
        }

        JwtClaimsSet claims = SecurityUtil.createClaims(user, tokenConfig.getAccess().duration());
        String accessToken = tokenCryptoService.createToken(claims, rsaEncrypter);
        accessTokenService.saveToken(
                AccessToken.builder()
                        .token(accessToken)
                        .user(user)
                        .build(),
                user.getId()
        );

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    public void logout() {
        String jweToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        accessTokenService.deleteToken(jweToken);
    }

    @Override
    public void logoutAllToken() {
        int userId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accessTokenService.deleteAllPresentTokenByUserId(userId);
    }
}
