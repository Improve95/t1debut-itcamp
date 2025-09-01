package ru.t1debut.itcamp.consent.core.security.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSessionDetails {

    private UserClaim userClaim;

    private String token;
}
