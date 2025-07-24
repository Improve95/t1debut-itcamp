package ru.improve.itcamp.auth.service.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ru.improve.itcamp.auth.service.api.ApiPaths.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERS)
public class UserController {

//    private final UserService userService;
//
//    @GetMapping()
//    public ResponseEntity<UserResponse> getUserByAuth() {
//        UserResponse userResponse = userService.getRefreshUserByAuth();
//        return ResponseEntity.ok(userResponse);
//    }
//
//    @PostMapping(BECOME + CLIENT)
//    public ResponseEntity<UserResponse> becomeClient() {
//        UserResponse userResponse = userService.becomeUserClient();
//        return ResponseEntity.ok(userResponse);
//    }
}
