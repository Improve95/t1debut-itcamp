package ru.t1debut.itcamp.auth.service.core.security.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.auth.service.api.exception.ServiceException;
import ru.t1debut.itcamp.auth.service.configuration.security.tokenConfig.JwsSignerConfig;
import ru.t1debut.itcamp.auth.service.core.security.object.JwtToken;
import ru.t1debut.itcamp.auth.service.core.security.service.TokenCryptoService;
import ru.t1debut.itcamp.auth.service.util.SecurityUtil;

import java.time.Instant;

import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.t1debut.itcamp.auth.service.util.MessageKeys.TITLE_UNAUTHORIZED;

@RequiredArgsConstructor
@Service
public class DefaultTokenCryptoService implements TokenCryptoService {

    @Override
    public String createToken(JWTClaimsSet claims, JWSSigner jwsSigner) {
        try {
            SignedJWT signedJWT = new SignedJWT(JwsSignerConfig.jwsHeader, claims);
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }

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
