package ru.t1debut.itcamp.auth.service.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1debut.itcamp.auth.service.api.controller.spec.UserControllerSpec;
import ru.t1debut.itcamp.auth.service.api.dto.user.UserResponse;
import ru.t1debut.itcamp.auth.service.core.service.UserService;

import static ru.t1debut.itcamp.auth.service.api.ApiPaths.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERS)
public class UserController implements UserControllerSpec {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getUserByAuth() {
        UserResponse userResponse = userService.getUserByAuth();
        return ResponseEntity.ok(userResponse);
    }
}
