package ru.t1debut.itcamp.consent.core.security.service;

import com.nimbusds.jose.JWSVerifier;
import org.springframework.security.oauth2.jwt.Jwt;

public interface TokenCryptoService {

    Jwt parseJweToken(String token, JWSVerifier jwsVerifier);

    boolean tokenIsExpired(Jwt token);
}
