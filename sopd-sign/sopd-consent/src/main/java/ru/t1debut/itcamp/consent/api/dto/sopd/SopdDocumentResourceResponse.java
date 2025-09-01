package ru.t1debut.itcamp.consent.api.dto.sopd;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.core.io.Resource;

@Data
@Builder
@Jacksonized
public class SopdDocumentResourceResponse {

    private Resource sopd;
}
