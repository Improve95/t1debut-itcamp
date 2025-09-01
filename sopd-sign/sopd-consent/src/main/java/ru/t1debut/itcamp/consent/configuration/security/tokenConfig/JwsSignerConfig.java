package ru.t1debut.itcamp.consent.configuration.security.tokenConfig;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1debut.itcamp.consent.util.SecurityUtil;

import java.security.interfaces.RSAPublicKey;

@RequiredArgsConstructor
@Configuration
public class JwsSignerConfig {

    private final TokenConfig tokenConfig;

    @Bean
    public JWSVerifier jwsVerifier() {
        return new RSASSAVerifier((RSAPublicKey) SecurityUtil.createPublicKey(tokenConfig.getJws().publicKeyPath()));
    }
}
