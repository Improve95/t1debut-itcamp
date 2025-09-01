package ru.t1debut.itcamp.consent.api.dto.consent;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.t1debut.itcamp.consent.model.consent.ConsentStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class ConsentResponse {

    private UUID id;

    private int sopdDocumentVersion;

    private int managerId;

    private Integer candidateProfileId;

    private ConsentStatus status;

    private Instant createdAt;

    private Instant updatedAt;
}
