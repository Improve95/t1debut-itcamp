package ru.improve.itcamp.auth.service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.itcamp.auth.service.api.controller.spec.AuthControllerSpec;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.refresh.RefreshAccessTokenRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.refresh.RefreshAccessTokenResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInResponse;
import ru.improve.itcamp.auth.service.core.security.service.AuthService;

import static ru.improve.itcamp.auth.service.api.ApiPaths.ACCESS_TOKEN;
import static ru.improve.itcamp.auth.service.api.ApiPaths.AUTH;
import static ru.improve.itcamp.auth.service.api.ApiPaths.LOGIN;
import static ru.improve.itcamp.auth.service.api.ApiPaths.LOGOUT;
import static ru.improve.itcamp.auth.service.api.ApiPaths.LOGOUT_ALL;
import static ru.improve.itcamp.auth.service.api.ApiPaths.REFRESH;
import static ru.improve.itcamp.auth.service.api.ApiPaths.SIGN_IN;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH)
public class AuthController implements AuthControllerSpec {

    private final AuthService authService;

    @PostMapping(SIGN_IN)
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        SignInResponse signInResponse = authService.signIn(signInRequest);
        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(ACCESS_TOKEN + REFRESH)
    public ResponseEntity<RefreshAccessTokenResponse> refreshAccessToken(
            @RequestBody @Valid RefreshAccessTokenRequest refreshAccessTokenRequest
    ) {
        RefreshAccessTokenResponse refreshAccessTokenResponse = authService.refreshAccessToken(
                refreshAccessTokenRequest
        );
        return ResponseEntity.ok(refreshAccessTokenResponse);
    }

    @PostMapping(LOGOUT)
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @PostMapping(LOGOUT_ALL)
    public ResponseEntity<Void> logoutAllTokens() {
        authService.logoutAllToken();
        return ResponseEntity.ok().build();
    }
}
