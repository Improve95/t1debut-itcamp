package ru.t1debut.itcamp.consent.api.dto.sopd;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UpdateSopdDocumentRequest {

    @NotBlank
    private String sopdDocumentHtmlBody;

    private boolean setPrime;
}
