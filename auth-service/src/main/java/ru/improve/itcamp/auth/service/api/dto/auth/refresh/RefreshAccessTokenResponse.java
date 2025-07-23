package ru.improve.itcamp.auth.service.api.dto.auth.refresh;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshAccessTokenResponse {

    private String accessToken;
}
