package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.security.interfaces.RSAPublicKey;

import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ACCESS_TOKEN;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ENCODER;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.REFRESH_TOKEN;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.createPublicKey;

@RequiredArgsConstructor
@Configuration
public class EncoderConfig {

    public static final JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
            .contentType("JWT")
            .build();

    private final TokenConfig tokenConfig;

    @Primary
    @Bean(name = ACCESS_TOKEN + ENCODER)
    public RSAEncrypter accessTokenRsaEncrypter() {
        return new RSAEncrypter((RSAPublicKey) createPublicKey(tokenConfig.getAccess().secret()));
    }

    @Bean(name = REFRESH_TOKEN + ENCODER)
    public RSAEncrypter refreshTokenRsaEncrypter() {
        return new RSAEncrypter((RSAPublicKey) createPublicKey(tokenConfig.getRefresh().secret()));
    }
}
