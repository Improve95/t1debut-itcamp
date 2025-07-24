package ru.improve.itcamp.auth.service.core.security.service;

import com.nimbusds.jose.util.Base64URL;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

public interface TokenService {
    Base64URL createToken(JwtClaimsSet claims);
}
