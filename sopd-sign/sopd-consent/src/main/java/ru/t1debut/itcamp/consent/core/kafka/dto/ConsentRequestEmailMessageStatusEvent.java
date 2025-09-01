package ru.t1debut.itcamp.consent.core.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import ru.t1debut.itcamp.consent.model.message.EmailMessageStatus;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class ConsentRequestEmailMessageStatusEvent {

    private UUID messageId;

    private EmailMessageStatus status;
}
