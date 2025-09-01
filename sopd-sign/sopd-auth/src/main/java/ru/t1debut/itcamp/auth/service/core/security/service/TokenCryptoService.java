package ru.t1debut.itcamp.auth.service.core.security.service;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.oauth2.jwt.Jwt;

public interface TokenCryptoService {

    String createToken(JWTClaimsSet claims, JWSSigner jwsSigner);

    Jwt parseJweToken(String token, JWSVerifier jwsVerifier);

    boolean tokenIsExpired(Jwt token);
}
