package ru.t1debut.itcamp.consent.api.dto.profile.create;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CreateCandidateProfileResponse {

    private int id;
}
