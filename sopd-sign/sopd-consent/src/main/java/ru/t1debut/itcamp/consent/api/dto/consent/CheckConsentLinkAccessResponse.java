package ru.t1debut.itcamp.consent.api.dto.consent;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CheckConsentLinkAccessResponse {

    private String email;

    private int sopdVersion;
}
