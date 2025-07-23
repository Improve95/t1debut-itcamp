package ru.improve.itcamp.auth.service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.login.LoginResponse;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInRequest;
import ru.improve.itcamp.auth.service.api.dto.auth.signin.SignInResponse;
import ru.improve.itcamp.auth.service.core.security.service.AuthService;

import static ru.improve.itcamp.auth.service.api.ApiPaths.AUTH;
import static ru.improve.itcamp.auth.service.api.ApiPaths.LOGIN;
import static ru.improve.itcamp.auth.service.api.ApiPaths.LOGOUT;
import static ru.improve.itcamp.auth.service.api.ApiPaths.SIGN_IN;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(SIGN_IN)
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        SignInResponse signInResponse = authService.signIn(signInRequest);
        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    /*@PostMapping(REFRESH + TOKEN)
    public ResponseEntity<RefreshAccessTokenResponse> refreshAccessToken(
            @PathVariable String token
    ) {
        RefreshAccessTokenResponse refreshAccessTokenResponse = authService.refreshAccessToken(token);
        return ResponseEntity.ok(refreshAccessTokenResponse);
    }*/

    @PostMapping(LOGOUT)
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    /*@PostMapping(LOGOUT_ALL)
    public ResponseEntity<Void> logoutAllSessions() {
        authService.logoutAllSessions();
        return ResponseEntity.ok().build();
    }*/

    /*@PostMapping(PASSWORD + RESET)
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordGetLinkRequest resetPasswordGetLinkRequest) {
        authService.sendLinkForResetPassword(resetPasswordGetLinkRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(PASSWORD + RESET + TOKEN)
    public ResponseEntity<Void> resetPassword(
            @PathVariable String token,
            @RequestBody ResetPasswordSendPasswordRequest resetPasswordSendPasswordRequest
    ) {
        authService.resetPassword(token, resetPasswordSendPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }*/
}
