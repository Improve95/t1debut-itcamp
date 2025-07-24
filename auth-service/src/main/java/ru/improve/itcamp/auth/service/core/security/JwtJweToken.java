package ru.improve.itcamp.auth.service.core.security;

import com.nimbusds.jose.JWEObject;
import lombok.Getter;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtJweToken extends Jwt {

    @Getter
    private final String jweToken;

    public JwtJweToken(
            String jweToken,
            JWEObject jweObject
    ) {
        super(
                jweObject.serialize(),
                null,
                null,
                jweObject.getHeader().toJSONObject(),
                jweObject.getPayload().toJSONObject()
        );
        this.jweToken = jweToken;
    }
}
