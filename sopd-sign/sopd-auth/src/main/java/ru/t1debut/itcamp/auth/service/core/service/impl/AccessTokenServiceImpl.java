package ru.t1debut.itcamp.auth.service.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.auth.service.core.repository.AccessTokenRepository;
import ru.t1debut.itcamp.auth.service.core.service.TokenService;
import ru.t1debut.itcamp.auth.service.model.AccessToken;
import ru.t1debut.itcamp.auth.service.model.User;

@RequiredArgsConstructor
@Service
public class AccessTokenServiceImpl implements TokenService {

    private final AccessTokenRepository tokenRepository;

    @Transactional
    @Override
    public boolean tokenIsPresent(String token) {
        boolean test = tokenRepository.existsAccessTokenByToken(token);
        return test;
    }

    @Transactional
    @Override
    public void saveToken(String token, User user) {
        tokenRepository.save(
                AccessToken.builder()
                        .token(token)
                        .user(user)
                        .build()
        );
    }

    @Transactional
    @Override
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Transactional
    @Override
    public void deleteAllPresentTokenByUserId(int userId) {
        tokenRepository.deleteAllByUserId(userId);
    }
}
