package ru.improve.itcamp.auth.service.api.dto.auth.refresh;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshAccessTokenRequest {

    @NotNull
    @NotBlank
    private String refreshToken;
}
