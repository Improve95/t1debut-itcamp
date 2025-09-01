package ru.t1debut.itcamp.consent.api.dto.profile.get;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GetCandidateProfilesRequest {

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Min(1)
    private Integer id;

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String email;

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phone;
}
