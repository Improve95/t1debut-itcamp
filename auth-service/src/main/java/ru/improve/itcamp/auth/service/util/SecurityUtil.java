package ru.improve.itcamp.auth.service.util;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;

import java.time.Instant;

public final class SecurityUtil {

    public static final String SESSION_ID_CLAIM = "sessionId";

    public static final String CLIENT_ROLE = "ROLE_CLIENT";

    public static final String OPERATOR_ROLE = "ROLE_OPERATOR";

    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    public static JwtClaimsSet createDefaultClaims(
            String username,
            Instant issuedAt,
            Instant expiresAt,
            long sessionId
    ) {
        return JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .claim(SecurityUtil.SESSION_ID_CLAIM, sessionId)
                .build();
    }
}
