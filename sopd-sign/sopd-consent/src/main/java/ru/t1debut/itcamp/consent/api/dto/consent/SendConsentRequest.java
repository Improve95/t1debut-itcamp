package ru.t1debut.itcamp.consent.api.dto.consent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_USE_CUSTOM_DOC_VER_PARAM_TAG;

@Data
@Builder
@Jacksonized
public class SendConsentRequest {

    @Schema(example = "email1@gmail.com")
    @NotBlank
    @Email
    @Size(max = 100)
    private String candidateEmail;

    @Schema(
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = SWAGGER_USE_CUSTOM_DOC_VER_PARAM_TAG
    )
    @Min(1)
    private Integer sopdDocumentVersion;
}
