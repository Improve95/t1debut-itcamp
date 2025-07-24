package ru.improve.itcamp.auth.service.api.dto.auth.login;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class LoginResponse {

    private long id;

    private String accessToken;

    private String refreshToken;
}
