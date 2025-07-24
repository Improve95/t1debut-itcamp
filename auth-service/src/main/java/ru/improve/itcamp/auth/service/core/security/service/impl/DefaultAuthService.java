package ru.improve.itcamp.auth.service.core.security.service.impl;

import com.nimbusds.jose.util.Base64URL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
import ru.improve.itcamp.auth.service.core.security.service.AuthService;
import ru.improve.itcamp.auth.service.core.security.service.TokenService;
import ru.improve.itcamp.auth.service.core.service.RoleService;
import ru.improve.itcamp.auth.service.core.service.UserService;
import ru.improve.itcamp.auth.service.model.User;
import ru.improve.itcamp.auth.service.util.SecurityUtil;
import ru.improve.itcamp.auth.service.util.mapper.UserMapper;

import java.util.Set;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.UNAUTHORIZED;

@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    private final TokenService tokenService;

    private final UserMapper userMapper;

    private final TokenConfig tokenConfig;

    @Override
    public boolean setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken)) {
            return true;
        }
        Object jwt = auth.getPrincipal();
        return true;
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
                .refreshToken(loginResponse.getRefreshToken())
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
        Base64URL accessToken = tokenService.createToken(claims);

        return LoginResponse.builder()
                .accessToken(accessToken.toString())
                .refreshToken(null)
                .build();
    }

    @Override
    public void logout() {

    }
}
