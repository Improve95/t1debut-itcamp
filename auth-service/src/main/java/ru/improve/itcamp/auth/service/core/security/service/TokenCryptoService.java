package ru.improve.itcamp.auth.service.core.security.service;

import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.oauth2.jwt.Jwt;

public interface TokenCryptoService {

    String createToken(JWTClaimsSet claims, RSAEncrypter rsaEncrypter);

    Jwt parseJweToken(String token, RSADecrypter rsaDecrypter);

    boolean tokenIsExpired(Jwt token);
}
