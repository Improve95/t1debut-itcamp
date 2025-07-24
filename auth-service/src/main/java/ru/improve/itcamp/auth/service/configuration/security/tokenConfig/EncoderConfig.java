package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.crypto.RSAEncrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ACCESS_TOKEN_CODER;

@RequiredArgsConstructor
@Configuration
public class EncoderConfig {

    public static final String JWE_ENCODER = "_jwe_encoder";

    private final ResourceLoader resourceLoader;

    @Bean(name = ACCESS_TOKEN_CODER + JWE_ENCODER)
    public RSAEncrypter clientJweEncoder() {
        return new RSAEncrypter((RSAPublicKey) getPublicKey());
    }

    private PublicKey getPublicKey() {
        try {
            Resource resource = resourceLoader.getResource("classpath:resources/keys/public/auth-public.pem");
            String[] splitedKey = resource.getContentAsString(StandardCharsets.UTF_8).split("\\n");
            String decoded = Arrays.stream(splitedKey)
                    .skip(1)
                    .limit(splitedKey.length - 2)
                    .collect(Collectors.joining());
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(decoded));
            return KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
