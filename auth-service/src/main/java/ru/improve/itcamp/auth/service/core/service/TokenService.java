package ru.improve.itcamp.auth.service.core.service;

import ru.improve.itcamp.auth.service.model.AccessToken;

public interface TokenService {

    boolean tokenIsPresent(String token, int userId);

    void saveToken(AccessToken token, int userId);

    void deleteToken(String token);

    void deleteAllPresentTokenByUserId(int userId);
}
