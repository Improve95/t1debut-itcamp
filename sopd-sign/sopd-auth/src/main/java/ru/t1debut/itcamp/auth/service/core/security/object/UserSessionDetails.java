package ru.t1debut.itcamp.auth.service.core.security.object;

import lombok.Data;

@Data
public class UserSessionDetails {

    public UserSessionDetails(UserClaim userClaim, String token) {
        this.userClaim = userClaim;
        this.token = token;
    }

    private UserClaim userClaim;

    private String token;
}
