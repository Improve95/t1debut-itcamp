package ru.improve.itcamp.auth.service.core.security.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.util.Base64URL;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.auth.service.api.exception.ServiceException;
import ru.improve.itcamp.auth.service.configuration.security.tokenConfig.EncoderConfig;
import ru.improve.itcamp.auth.service.core.security.service.TokenService;

import java.security.interfaces.RSAPublicKey;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@Service
public class DefaultTokenService implements TokenService {

    private final EncoderConfig encoderConfig;

    @Override
    public Base64URL createToken(JwtClaimsSet claims) {
        try {
            Payload payload = new Payload(claims.getClaims());
            JWEObject jweObject = new JWEObject(EncoderConfig.jweHeader, payload);
            jweObject.encrypt(new RSAEncrypter((RSAPublicKey) encoderConfig.getPublicKey()));
            return jweObject.getEncryptedKey();
        } catch (JOSEException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }
}
