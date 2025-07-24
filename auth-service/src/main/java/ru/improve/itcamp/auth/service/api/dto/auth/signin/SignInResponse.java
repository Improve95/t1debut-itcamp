package ru.improve.itcamp.auth.service.api.dto.auth.signin;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SignInResponse {

    private int id;
}
