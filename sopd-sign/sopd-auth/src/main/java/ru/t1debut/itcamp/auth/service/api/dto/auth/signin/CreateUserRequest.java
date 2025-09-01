package ru.t1debut.itcamp.auth.service.api.dto.auth.signin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Schema(
            example = "gmail1@gmail.com"
    )
    @NotBlank
    @Email
    @Size(min = 5, max = 100)
    private String email;
}
