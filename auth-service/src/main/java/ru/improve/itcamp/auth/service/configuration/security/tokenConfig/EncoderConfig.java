package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import ru.improve.itcamp.auth.service.api.exception.ServiceException;
import ru.improve.itcamp.auth.service.util.SecurityUtil;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ACCESS_TOKEN;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ENCODER;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.REFRESH_TOKEN;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.KEY_FILE_EXTENSION;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.PUBLIC_KEY;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.PUBLIC_KEY_FILE_PATH;

@RequiredArgsConstructor
@Configuration
public class EncoderConfig {

    public static final JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
            .contentType("JWT")
            .build();

    private final ResourceLoader resourceLoader;

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

    private PublicKey createPublicKey(String keyFileName) {
        try {
            String decoded = SecurityUtil.getDecodeKeyFromFile(
                    resourceLoader,
                    PUBLIC_KEY_FILE_PATH + keyFileName + PUBLIC_KEY + KEY_FILE_EXTENSION
            );
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(decoded));
            return KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }
}
