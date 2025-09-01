package ru.t1debut.itcamp.consent.core.security.service.impl;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.core.security.object.JwtToken;
import ru.t1debut.itcamp.consent.core.security.service.TokenCryptoService;
import ru.t1debut.itcamp.consent.util.SecurityUtil;

import java.time.Instant;

import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_UNAUTHORIZED;

@RequiredArgsConstructor
@Service
public class DefaultTokenCryptoService implements TokenCryptoService {

    @Override
    public Jwt parseJweToken(String token, JWSVerifier jwsVerifier) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            signedJWT.verify(jwsVerifier);
            return new JwtToken(signedJWT);
        } catch (Exception ex) {
            throw new JwtException(TITLE_UNAUTHORIZED, ex);
        }
    }

    @Override
    public boolean tokenIsExpired(Jwt token) {
        long expiresAt = token.getClaim(SecurityUtil.EXPIRES_AT_CLAIM);
        return Instant.ofEpochMilli(expiresAt).isBefore(Instant.now());
    }
}
