package ru.improve.itcamp.auth.service.core.security;

import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import org.springframework.security.oauth2.jwt.Jwt;

public class JweJwtToken extends Jwt {

    @Getter
    private final String jweToken;

    public JweJwtToken(
            String jweToken,
            SignedJWT signedJWT
    ) {
        super(
                signedJWT.serialize(),
                null,
                null,
                signedJWT.getHeader().toJSONObject(),
                signedJWT.getPayload().toJSONObject()
        );
        this.jweToken = jweToken;
    }
}
