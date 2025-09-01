package ru.t1debut.itcamp.consent.core.security.object;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UserClaim {

    private int id;

    private String email;

    private String firstName;
}
