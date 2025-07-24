package ru.improve.itcamp.auth.service.core.service.impl.token;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.core.repository.RefreshTokenRepository;
import ru.improve.itcamp.auth.service.core.service.TokenService;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements TokenService {

    private final RefreshTokenRepository tokenRepository;

    @Transactional
    @Override
    public boolean tokenIsPresent(String token, int userId) {
        return tokenRepository.existsAccessTokenByTokenAndUserId(token, userId);
    }

    @Transactional
    @Override
    public void saveToken(String token, int userId) {
        tokenRepository.saveToken(token, userId);
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
