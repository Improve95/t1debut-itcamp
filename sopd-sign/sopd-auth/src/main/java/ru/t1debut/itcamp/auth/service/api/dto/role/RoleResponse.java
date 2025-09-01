package ru.t1debut.itcamp.auth.service.api.dto.role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {

    private int id;

    private String name;
}
