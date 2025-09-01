package ru.t1debut.itcamp.consent.api.dto.consent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import ru.t1debut.itcamp.consent.model.consent.ConsentStatus;

import java.util.UUID;

@Data
@Builder
public class GetConsentRequest {

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private UUID id;

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Min(1)
    private Integer managerId;

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private ConsentStatus status;

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String candidateEmail;
}
