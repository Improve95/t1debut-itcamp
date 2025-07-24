package ru.improve.itcamp.auth.service.core.service.impl.token;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.core.repository.AccessTokenRepository;
import ru.improve.itcamp.auth.service.core.repository.UserRepository;
import ru.improve.itcamp.auth.service.core.service.TokenService;
import ru.improve.itcamp.auth.service.model.AccessToken;

@RequiredArgsConstructor
@Service
public class AccessTokenServiceImpl implements TokenService {

    private final AccessTokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public boolean tokenIsPresent(String token, int userId) {
        return tokenRepository.existsAccessTokenByTokenAndUserId(token, userId);
    }

    @Transactional
    @Override
    public void saveToken(AccessToken token, int userId) {
        tokenRepository.saveToken(token.getToken(), userId);
    }

    @Transactional
    @Override
    public void deleteToken(String token) {
        tokenRepository.deleteById(token);
    }

    @Transactional
    @Override
    public void deleteAllPresentTokenByUserId(int userId) {
        tokenRepository.deleteAllByUserId(userId);
    }
}
