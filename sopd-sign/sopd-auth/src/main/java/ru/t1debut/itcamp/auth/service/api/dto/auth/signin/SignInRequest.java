package ru.t1debut.itcamp.auth.service.api.dto.auth.signin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {

    @Schema(
            example = "gmail1@gmail.com"
    )
    @NotNull
    @NotBlank
    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Schema(
            example = "password1"
    )
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;

    @Schema(
            example = "name1"
    )
    @NotNull
    @NotBlank
    @Size(min = 5, max = 100)
    private String firstName;
}
