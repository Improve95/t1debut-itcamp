package ru.t1debut.itcamp.notification.service.domain;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailStatusEvent {
    private UUID messageId;
    private EmailStatus status;

    @Builder.Default
    private OffsetDateTime occurredAt = OffsetDateTime.now();
}
