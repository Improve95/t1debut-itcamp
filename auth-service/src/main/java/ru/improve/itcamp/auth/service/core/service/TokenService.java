package ru.improve.itcamp.auth.service.core.service;

public interface TokenService {

    boolean tokenIsPresent(String token, int userId);

    void saveToken(String token, int userId);

    void deleteToken(String token);

    void deleteAllPresentTokenByUserId(int userId);
}
