package ru.improve.itcamp.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import static ru.improve.itcamp.auth.service.configuration.security.tokenConfig.CoderNames.ACCESS_TOKEN_CODER;
import static ru.improve.itcamp.auth.service.util.MessageKeys.TITLE_UNAUTHORIZED;

@RequiredArgsConstructor
@Configuration
public class DecoderConfig {

     public static final String JWE_DECODER = "_jwe_decoder";

     private final ResourceLoader resourceLoader;

     @Bean(name = ACCESS_TOKEN_CODER + JWE_DECODER)
     public JwtDecoder clientJwtDecoder() {
          PrivateKey privateKey = getPrivateKey();
          return token -> {
               try {
                    EncryptedJWT jwt = EncryptedJWT.parse(token);
                    RSADecrypter decrypter = new RSADecrypter(privateKey);
                    jwt.decrypt(decrypter);
                    return Jwt.withTokenValue(jwt.getParsedString()).build();
               } catch (Exception ex) {
                    throw new JwtException(TITLE_UNAUTHORIZED, ex);
               }
          };
     }

     private PrivateKey getPrivateKey() {
         try {
              Resource resource = resourceLoader.getResource("classpath:/keys/private/auth-private.pem");
              String[] splitedKey = resource.getContentAsString(StandardCharsets.UTF_8).split("\\n");
              String decoded = Arrays.stream(splitedKey)
                      .skip(1)
                      .limit(splitedKey.length - 2)
                      .collect(Collectors.joining());
              EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(decoded));
              return KeyFactory.getInstance("RSA").generatePrivate(spec);
         } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException ex) {
             throw new RuntimeException(ex);
         }
     }
}
