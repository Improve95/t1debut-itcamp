package ru.improve.itcamp.auth.service.core.security.service.impl;

import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.refresh.RefreshAccessTokenRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.refresh.RefreshAccessTokenResponse;
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
import ru.improve.itcamp.auth.service.model.User;
import ru.improve.itcamp.auth.service.util.SecurityUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.EXPIRED;
import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.UNAUTHORIZED;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ACCESS_TOKEN;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.DECODER;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ENCODER;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.REFRESH_TOKEN;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.AUTHORITIES_CLAIM;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    @Qualifier("accessTokenServiceImpl")
    private TokenService accessTokenService;

    @Autowired
    @Qualifier("refreshTokenServiceImpl")
    private TokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    private final TokenCryptoService tokenCryptoService;

    private final TokenConfig tokenConfig;

    private final Map<String, RSAEncrypter> rsaEncrypters;

    private final Map<String, RSADecrypter> rsaDecrypters;

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

            if (!accessTokenService.tokenIsPresent(token.getTokenValue(), userId)) {
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
            if (ex instanceof ServiceException e && e.getCode() == EXPIRED) {
                throw e;
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
        return SignInResponse.builder()
                .id(user.getId())
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
        String accessToken = tokenCryptoService.createToken(claims, rsaEncrypters.get(ACCESS_TOKEN + ENCODER));

        claims = SecurityUtil.createClaims(user, tokenConfig.getAccess().duration());
        String refreshToken = tokenCryptoService.createToken(claims, rsaEncrypters.get(REFRESH_TOKEN + ENCODER));

        accessTokenService.saveToken(accessToken, user.getId());
        refreshTokenService.saveToken(refreshToken, user.getId());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            Jwt token = tokenCryptoService.parseJweToken(refreshToken, rsaDecrypters.get(REFRESH_TOKEN + DECODER));
            int userId = Integer.parseInt(token.getSubject());

            if (tokenCryptoService.tokenIsExpired(token)) {
                throw new ServiceException(EXPIRED);
            }

            if (!refreshTokenService.tokenIsPresent(token.getTokenValue(), userId)) {
                throw new ServiceException(UNAUTHORIZED);
            }

            User user = userService.findUser(userId);
            JwtClaimsSet claims = SecurityUtil.createClaims(
                    user,
                    tokenConfig.getAccess().duration()
            );
            String accessToken = tokenCryptoService.createToken(claims, rsaEncrypters.get(ACCESS_TOKEN + ENCODER));
            accessTokenService.saveToken(accessToken, user.getId());

            return RefreshAccessTokenResponse.builder()
                    .accessToken(accessToken)
                    .build();
        } catch (Exception ex) {
            throw new ServiceException(UNAUTHORIZED);
        }
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
        refreshTokenService.deleteAllPresentTokenByUserId(userId);
    }
}
