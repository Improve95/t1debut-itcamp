package ru.improve.itcamp.auth.service.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import ru.improve.itcamp.auth.service.api.exception.ServiceException;
import ru.improve.itcamp.auth.service.model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.improve.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

public final class SecurityUtil {

    public static final String EXPIRES_AT_CLAIM = "expiresAt";

    public static final String AUTHORITIES_CLAIM = "userAuthorities";

    public static final String CLIENT_ROLE = "ROLE_CLIENT";

    public static final String OPERATOR_ROLE = "ROLE_OPERATOR";

    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    public static final String PUBLIC_KEY_FILE_PATH = "classpath:/keys/public/auth-public.pem";

    public static final String PRIVATE_KEY_FILE_PATH = "classpath:/keys/private/auth-private.pem";

    public static JwtClaimsSet createClaims(
            User user,
            Duration tokenDuration
    ) {
        return JwtClaimsSet.builder()
                .subject(String.valueOf(user.getId()))
                .claim(EXPIRES_AT_CLAIM, Instant.now().plus(tokenDuration).toEpochMilli())
                .claim(AUTHORITIES_CLAIM, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    public static String getDecodeKeyFromFile(ResourceLoader resourceLoader, String filePath) {
        try {
            Resource resource = resourceLoader.getResource(filePath);
            String[] splitedKey = resource.getContentAsString(StandardCharsets.UTF_8).split("\\n");
            return Arrays.stream(splitedKey)
                    .skip(1)
                    .limit(splitedKey.length - 2)
                    .collect(Collectors.joining());
        } catch (IOException e) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }
}
