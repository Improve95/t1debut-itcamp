package ru.t1debut.itcamp.auth.service.util;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.t1debut.itcamp.auth.service.api.exception.ServiceException;
import ru.t1debut.itcamp.auth.service.core.security.object.UserClaim;
import ru.t1debut.itcamp.auth.service.core.security.object.UserSessionDetails;
import ru.t1debut.itcamp.auth.service.model.User;

import java.io.FileInputStream;
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

import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

public final class SecurityUtil {

    public static final String ISSUED_AT_CLAIM = "issuedAt";

    public static final String EXPIRES_AT_CLAIM = "expiresAt";

    public static final String AUTHORITIES_CLAIM = "userAuthorities";

    public static final String USER_CLAIM = "userData";

    public static final String MANAGER_ROLE = "ROLE_MANAGER";

    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    private static final Gson gson = new Gson();

    public static JWTClaimsSet createClaims(
            User user,
            Duration tokenDuration
    ) {
        Instant issuedAt = Instant.now();
        return new JWTClaimsSet.Builder()
                .subject(String.valueOf(user.getId()))
                .claim(ISSUED_AT_CLAIM, issuedAt.toEpochMilli())
                .claim(EXPIRES_AT_CLAIM, issuedAt.plus(tokenDuration).toEpochMilli())
                .claim(USER_CLAIM, new UserClaim(user))
                .claim(AUTHORITIES_CLAIM, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    public static PrivateKey createPrivateKey(String keyFilePath) {
        try {
            String decoded = SecurityUtil.getDecodeKeyFromFile(keyFilePath).replaceAll("\\s", "");
            EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(decoded));
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static PublicKey createPublicKey(String keyFilePath) {
        try {
            String decoded = SecurityUtil.getDecodeKeyFromFile(keyFilePath);
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(decoded));
            return KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }

    public static String getDecodeKeyFromFile(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            String[] splitedKey = new String(inputStream.readAllBytes()).split("\\n");
            return Arrays.stream(splitedKey)
                    .skip(1)
                    .limit(splitedKey.length - 2)
                    .collect(Collectors.joining())
                    .replaceAll("\\s", "");
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

    public static <T> T getClaimObjectFromToken(Jwt token, String claim, Class<T> clazz) {
        var test = token.getClaims().get(claim);
        return gson.fromJson(gson.toJson(test), clazz);
    }
}
