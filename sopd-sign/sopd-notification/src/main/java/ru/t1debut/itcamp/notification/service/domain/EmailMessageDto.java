package ru.t1debut.itcamp.notification.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EmailMessageDto(
        @NotBlank UUID messageId,
        @NotBlank @Email String from,
        @NotBlank @Email String to,
        @NotBlank String title,
        @NotBlank String body,
        boolean isHtmlBody
) {}
