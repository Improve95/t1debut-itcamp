package ru.t1debut.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1debut.itcamp.auth.service.util.SecurityUtil;

import java.security.interfaces.RSAPublicKey;

@RequiredArgsConstructor
@Configuration
public class JwsSignerConfig {

    public static final JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).build();

    private final TokenConfig tokenConfig;

    @Bean
    public JWSSigner jwsSigner(TokenConfig tokenConfig) {
        return new RSASSASigner(SecurityUtil.createPrivateKey(tokenConfig.getJws().privateKeyPath()));
    }

    @Bean
    public JWSVerifier jwsVerifier() {
        return new RSASSAVerifier((RSAPublicKey) SecurityUtil.createPublicKey(tokenConfig.getJws().publicKeyPath()));
    }
}
