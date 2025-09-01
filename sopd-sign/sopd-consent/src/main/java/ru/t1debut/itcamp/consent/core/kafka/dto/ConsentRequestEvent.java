package ru.t1debut.itcamp.consent.core.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class ConsentRequestEvent {

    private String from;

    private String to;

    private String title;

    private String body;

    private UUID messageId;

    @JsonProperty("isHtmlBody")
    private boolean isHtmlBody;
}
