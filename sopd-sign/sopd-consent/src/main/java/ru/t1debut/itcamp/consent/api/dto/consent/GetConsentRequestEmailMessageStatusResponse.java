package ru.t1debut.itcamp.consent.api.dto.consent;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.t1debut.itcamp.consent.model.message.EmailMessageStatus;

@Data
@Builder
@Jacksonized
public class GetConsentRequestEmailMessageStatusResponse {

    private EmailMessageStatus status;
}
