package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.crypto.RSADecrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import ru.improve.itcamp.auth.service.core.security.service.TokenCryptoService;

import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ACCESS_TOKEN;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.DECODER;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.REFRESH_TOKEN;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.createPrivateKey;

@RequiredArgsConstructor
@Configuration
public class DecoderConfig {

     private final TokenCryptoService tokenCryptoService;

     private final TokenConfig tokenConfig;

     @Bean
     public JwtDecoder clientJwtDecoder(RSADecrypter rsaDecrypter) {
          return token -> tokenCryptoService.parseJweToken(token, rsaDecrypter);
     }

     @Primary
     @Bean(name = ACCESS_TOKEN + DECODER)
     public RSADecrypter accessTokenRsaDecrypter() {
          return new RSADecrypter(createPrivateKey(tokenConfig.getAccess().secret()));
     }

    @Bean(name = REFRESH_TOKEN + DECODER)
    public RSADecrypter refreshTokenRsaDecrypter() {
        return new RSADecrypter(createPrivateKey(tokenConfig.getRefresh().secret()));
    }
}
