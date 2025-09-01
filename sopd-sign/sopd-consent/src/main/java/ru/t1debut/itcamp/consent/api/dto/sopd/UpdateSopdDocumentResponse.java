package ru.t1debut.itcamp.consent.api.dto.sopd;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UpdateSopdDocumentResponse {

    private int version;
}
