package ru.t1debut.itcamp.consent.api.dto.email;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UpdateEmailFormRequest {

    @NotBlank
    private String htmlEmailBody;

    private boolean setPrime;
}
