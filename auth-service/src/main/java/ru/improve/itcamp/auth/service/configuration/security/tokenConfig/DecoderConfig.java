package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.crypto.RSADecrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import ru.improve.itcamp.auth.service.core.security.service.TokenCryptoService;
import ru.improve.itcamp.auth.service.util.SecurityUtil;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ACCESS_TOKEN;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.DECODER;
import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.REFRESH_TOKEN;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.KEY_FILE_EXTENSION;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.PRIVATE_KEY;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.PRIVATE_KEY_FILE_PATH;

@RequiredArgsConstructor
@Configuration
public class DecoderConfig {

     private final ResourceLoader resourceLoader;

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

     private PrivateKey createPrivateKey(String keyFileName) {
         try {
              String decoded = SecurityUtil.getDecodeKeyFromFile(
                      resourceLoader,
                      PRIVATE_KEY_FILE_PATH + keyFileName + PRIVATE_KEY + KEY_FILE_EXTENSION
              );
              EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(decoded));
              return KeyFactory.getInstance("RSA").generatePrivate(spec);
         } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
             throw new RuntimeException(ex);
         }
     }
}
