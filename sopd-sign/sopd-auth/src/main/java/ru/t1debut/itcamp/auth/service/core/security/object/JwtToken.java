package ru.t1debut.itcamp.auth.service.core.security.object;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtToken extends Jwt {

    private final SignedJWT signedJWT;

    public String getToken() {
        return signedJWT.serialize();
    }

    public JwtToken(SignedJWT signedJWT) {
        super(
                signedJWT.serialize(),
                null,
                null,
                signedJWT.getHeader().toJSONObject(),
                signedJWT.getPayload().toJSONObject()
        );
        this.signedJWT = signedJWT;
    }
}
