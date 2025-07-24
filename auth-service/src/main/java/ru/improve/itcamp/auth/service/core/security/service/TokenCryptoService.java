package ru.improve.itcamp.auth.service.core.security.service;

import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

public interface TokenCryptoService {

    String createToken(JwtClaimsSet claims, RSAEncrypter rsaEncrypter);

    Jwt parseJweToken(String token, RSADecrypter rsaDecrypter);

    boolean tokenIsExpired(Jwt token);
}
