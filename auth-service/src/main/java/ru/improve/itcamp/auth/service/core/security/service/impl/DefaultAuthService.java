package ru.improve.itcamp.auth.service.core.security.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInResponse;
import ru.improve.itcamp.auth.service.core.security.service.AuthService;

@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

    @Override
    public boolean setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public void logout() {

    }
}
