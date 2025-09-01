package ru.t1debut.itcamp.consent.api.dto.profile.get;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@Jacksonized
public class CandidateProfileResponse {

    private int id;

    private String email;

    private String firstName;

    private String secondName;

    private String thirdName;

    private LocalDate birthdate;

    private String phone;

    private Instant createdAt;

    private Instant updatedAt;
}
