package ru.improve.itcamp.auth.service.util;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.improve.itcamp.auth.service.api.exception.ServiceException;
import ru.improve.itcamp.auth.service.core.security.object.UserSessionDetails;
import ru.improve.itcamp.auth.service.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

public final class SecurityUtil {

    public static final String ISSUED_AT_CLAIM = "issuedAt";

    public static final String EXPIRES_AT_CLAIM = "expiresAt";

    public static final String AUTHORITIES_CLAIM = "userAuthorities";

    public static final String CLIENT_ROLE = "ROLE_CLIENT";

    public static final String OPERATOR_ROLE = "ROLE_OPERATOR";

    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    public static final String PUBLIC_KEY_FILE_PATH = "keys/public/";

    public static final String PRIVATE_KEY_FILE_PATH = "keys/private/";

    public static final String PUBLIC_KEY = "-public";

    public static final String PRIVATE_KEY = "-private";

    public static final String KEY_FILE_EXTENSION = ".pem";

    public static JWTClaimsSet createClaims(
            User user,
            Duration tokenDuration
    ) {
        Instant issuedAt = Instant.now();
        return new JWTClaimsSet.Builder()
                .subject(String.valueOf(user.getId()))
                .claim(ISSUED_AT_CLAIM, issuedAt.toEpochMilli())
                .claim(EXPIRES_AT_CLAIM, issuedAt.plus(tokenDuration).toEpochMilli())
                .claim(AUTHORITIES_CLAIM, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
        /*return JwtClaimsSet.builder()
                .subject(String.valueOf(user.getId()))
                .claim(ISSUED_AT_CLAIM, issuedAt.toEpochMilli())
                .claim(EXPIRES_AT_CLAIM, issuedAt.plus(tokenDuration).toEpochMilli())
                .claim(AUTHORITIES_CLAIM, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();*/
    }

    public static PrivateKey createPrivateKey(String keyFileName) {
        try {
            String decoded = SecurityUtil.getDecodeKeyFromFile(
                    PRIVATE_KEY_FILE_PATH + keyFileName + PRIVATE_KEY + KEY_FILE_EXTENSION
            );
            EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(decoded));
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static PublicKey createPublicKey(String keyFileName) {
        try {
            String decoded = SecurityUtil.getDecodeKeyFromFile(
                    PUBLIC_KEY_FILE_PATH + keyFileName + PUBLIC_KEY + KEY_FILE_EXTENSION
            );
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(decoded));
            return KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }

    public static String getDecodeKeyFromFile(String filePath) {
        try (InputStream inputStream = SecurityUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            String[] splitedKey = new String(inputStream.readAllBytes()).split("\\n");
            return Arrays.stream(splitedKey)
                    .skip(1)
                    .limit(splitedKey.length - 2)
                    .collect(Collectors.joining());
        } catch (IOException e) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }

    public static int getUserIdByAuth() {
        return (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static UserSessionDetails getSessionDetailsByAuth() {
        return (UserSessionDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
