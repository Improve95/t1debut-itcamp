package ru.t1debut.itcamp.consent.api.dto.profile.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static ru.t1debut.itcamp.consent.util.MessageKeys.TITLE_BAD_PHONE_FORMAT;

@Data
@Builder
@Jacksonized
public class CreateCandidateProfileRequest {

    @Schema(example = "firstname1")
    @NotBlank
    @Size(min = 5, max = 100)
    private String firstName;

    @Schema(example = "secondname1")
    @NotBlank
    @Size(min = 5, max = 100)
    private String secondName;

    @Schema(example = "thirdname1")
    @Size(min = 5, max = 100)
    private String thirdName;

    @Schema(example = "2002-02-12")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @Schema(example = "+79091235678")
    @Pattern(
            regexp = "^[\\+]{1}[0-9]{1,3}[0-9]{3}[0-9]{3}[0-9]{4}$",
            message = TITLE_BAD_PHONE_FORMAT
    )
    private String phone;
}
