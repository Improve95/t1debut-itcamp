package ru.improve.itcamp.auth.service.api.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Data
@Builder
@Jacksonized
public class UserResponse {

    private int id;

    private String email;

    private Set<Integer> rolesId;

    private String name;

    private String employment;
}
