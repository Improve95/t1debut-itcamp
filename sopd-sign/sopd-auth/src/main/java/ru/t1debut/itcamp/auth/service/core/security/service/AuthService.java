package ru.t1debut.itcamp.auth.service.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.t1debut.itcamp.auth.service.api.dto.auth.login.LoginRequest;
import ru.t1debut.itcamp.auth.service.api.dto.auth.login.LoginResponse;
import ru.t1debut.itcamp.auth.service.api.dto.auth.signin.CreateUserRequest;
import ru.t1debut.itcamp.auth.service.api.dto.auth.signin.CreateUserResponse;
import ru.t1debut.itcamp.auth.service.api.dto.auth.signin.SignInRequest;
import ru.t1debut.itcamp.auth.service.api.dto.auth.signin.SignInResponse;

public interface AuthService {

    boolean setAuthentication(HttpServletRequest request, HttpServletResponse response);

    SignInResponse signIn(SignInRequest signInRequest);

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    LoginResponse login(LoginRequest loginRequest);

    void logout();

    void logoutAllToken();
}
