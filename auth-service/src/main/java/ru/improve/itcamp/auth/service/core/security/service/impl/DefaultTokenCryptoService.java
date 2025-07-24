package ru.improve.itcamp.auth.service.core.security.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.api.exception.ServiceException;
import ru.improve.itcamp.auth.service.configuration.security.tokenConfig.EncoderConfig;
import ru.improve.itcamp.auth.service.core.security.JwtJweToken;
import ru.improve.itcamp.auth.service.core.security.service.TokenCryptoService;
import ru.improve.itcamp.auth.service.util.SecurityUtil;

import java.time.Instant;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.improve.itcamp.auth.service.util.MessageKeys.TITLE_UNAUTHORIZED;

@RequiredArgsConstructor
@Service
public class DefaultTokenCryptoService implements TokenCryptoService {

    @Override
    public String createToken(JwtClaimsSet claims, RSAEncrypter rsaEncrypter) {
        try {
            Payload payload = new Payload(claims.getClaims());
            JWEObject jweObject = new JWEObject(EncoderConfig.jweHeader, payload);
            jweObject.encrypt(rsaEncrypter);
            return jweObject.serialize();
        } catch (JOSEException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Jwt parseJweToken(String token, RSADecrypter rsaDecrypter) {
        try {
            JWEObject jweObject = JWEObject.parse(token);
            jweObject.decrypt(rsaDecrypter);
            return new JwtJweToken(token, jweObject);
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
