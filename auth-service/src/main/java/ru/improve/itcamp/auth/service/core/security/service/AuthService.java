package ru.improve.itcamp.auth.service.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.refresh.RefreshAccessTokenRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.refresh.RefreshAccessTokenResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInResponse;

public interface AuthService {

    boolean setAuthentication(HttpServletRequest request, HttpServletResponse response);

    SignInResponse signIn(SignInRequest signInRequest);

    LoginResponse login(LoginRequest loginRequest);

    RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest request);

    void logout();

    void logoutAllToken();
}
