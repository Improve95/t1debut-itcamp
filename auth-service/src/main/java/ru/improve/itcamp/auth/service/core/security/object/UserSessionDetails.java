package ru.improve.itcamp.auth.service.core.security.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSessionDetails {

    private String token;
}
