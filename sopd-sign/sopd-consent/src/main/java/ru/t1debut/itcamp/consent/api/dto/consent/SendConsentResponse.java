package ru.t1debut.itcamp.consent.api.dto.consent;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class SendConsentResponse {

    private UUID id;
}
