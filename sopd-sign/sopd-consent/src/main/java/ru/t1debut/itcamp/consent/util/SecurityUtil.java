package ru.t1debut.itcamp.consent.util;

import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.t1debut.itcamp.consent.api.exception.ServiceException;
import ru.t1debut.itcamp.consent.core.security.object.UserClaim;
import ru.t1debut.itcamp.consent.core.security.object.UserSessionDetails;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;


public final class SecurityUtil {

    public static final String EXPIRES_AT_CLAIM = "expiresAt";

    public static final String AUTHORITIES_CLAIM = "userAuthorities";

    public static final String USER_CLAIM = "userData";

    public static final String MANAGER_ROLE = "ROLE_MANAGER";

    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    private static final Gson gson = new Gson();

    public static PublicKey createPublicKey(String keyFileName) {
        try {
            String decoded = SecurityUtil.getDecodeKeyFromFile(keyFileName);
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

    public static int getRequestUserIdByAuth() {
        return (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static UserClaim getRequestUserClaim() {
        UserSessionDetails userSessionDetails = (UserSessionDetails) SecurityContextHolder.getContext().getAuthentication()
                .getDetails();
        return userSessionDetails.getUserClaim();
    }

    public static String getSessionToken() {
        return getSessionDetailsByAuth().getToken();
    }

    public static UserSessionDetails getSessionDetailsByAuth() {
        return (UserSessionDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    public static <T> T getClaimObjectFromToken(Jwt token, String claim, Class<T> clazz) {
        var test = token.getClaims().get(claim);
        return gson.fromJson(gson.toJson(test), clazz);
    }
}
