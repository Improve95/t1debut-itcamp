package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;

@Configuration
public class TokenCoderConfig {

     public static final String ACCESS_TOKEN_CODER = "access_token_coder";

//     public static final String REFRESH_TOKEN_CODER = "refresh_token_coder";

//     public static final String RESET_PASSWORD_TOKEN_CODER = "reset_password_token_coder";

     public static final String JWT_ENCODER = "_jwt_encoder";

     public static final String JWT_DECODER = "_jwt_decoder";

     private final TokenConfig.AccessTokenConfig accessTokenConfig;

     public TokenCoderConfig(TokenConfig tokenConfig) {
          this.accessTokenConfig = tokenConfig.getAccess();
     }

     @Bean(name = ACCESS_TOKEN_CODER + JWT_DECODER)
     public JwtDecoder clientJwtDecoder() {
          return NimbusJwtDecoder.withSecretKey(secretKey(accessTokenConfig.secret())).build();
     }

     @Bean(name = ACCESS_TOKEN_CODER + JWT_ENCODER)
     public JwtEncoder clientJwtEncoder() {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey(accessTokenConfig.secret())));
     }

     private SecretKey secretKey(String secretKey) {
          return new OctetSequenceKey.Builder(secretKey.getBytes()).build().toSecretKey();
     }
}
