package ru.improve.itcamp.auth.service.util;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import ru.improve.itcamp.auth.service.model.User;

import java.time.Duration;
import java.time.Instant;

public final class SecurityUtil {

    public static final String ISSUED_AT_CLAIM = "issuedAt";

    public static final String EXPIRES_AT_CLAIM = "expiresAt";

    public static final String AUTHORITIES_CLAIM = "userAuthorities";

    public static final String CLIENT_ROLE = "ROLE_CLIENT";

    public static final String OPERATOR_ROLE = "ROLE_OPERATOR";

    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    public static JwtClaimsSet createClaims(
            User user,
            Duration tokenDuration
    ) {
        Instant issuedAt = Instant.now();
        return JwtClaimsSet.builder()
                .subject(String.valueOf(user.getId()))
                .claim(ISSUED_AT_CLAIM, issuedAt.toEpochMilli())
                .claim(EXPIRES_AT_CLAIM, issuedAt.plus(tokenDuration).toEpochMilli())
                .claim(AUTHORITIES_CLAIM, user.getAuthorities())
                .build();
    }
}
