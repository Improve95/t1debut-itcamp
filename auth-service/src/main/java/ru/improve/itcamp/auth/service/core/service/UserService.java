package ru.improve.itcamp.auth.service.core.service;

import ru.improve.itcamp.auth.service.api.dto.user.UserResponse;
import ru.improve.itcamp.auth.service.model.User;

public interface UserService {

    UserResponse getUser(int id);

    User saveUser(User user);

    UserResponse getUserByAuth();

    User findUser(int id);
}
