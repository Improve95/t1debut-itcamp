package ru.t1debut.itcamp.auth.service.api.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class UserResponse {

    private int id;

    private String email;

    private Set<Integer> rolesId;

    private String firstName;

    private Instant createdAt;
}
