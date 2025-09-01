package ru.t1debut.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.JWSVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import ru.t1debut.itcamp.auth.service.core.security.service.TokenCryptoService;

@RequiredArgsConstructor
@Configuration
public class DecoderConfig {

     private final TokenCryptoService tokenCryptoService;

     private final JWSVerifier jwsVerifier;

     @Bean
     public JwtDecoder clientJwtDecoder() {
          return token -> tokenCryptoService.parseJweToken(token, jwsVerifier);
     }
}
