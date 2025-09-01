package ru.t1debut.itcamp.consent.api.dto.email;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UpdateEmailFormResponse {

    private int version;
}
