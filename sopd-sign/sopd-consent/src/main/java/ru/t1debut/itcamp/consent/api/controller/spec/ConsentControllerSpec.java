package ru.t1debut.itcamp.consent.api.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseAlreadyExist;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseIllegalDtoValue;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseIllegalValue;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseInternalServerError;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseInvalidRequest;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseNotFound;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseOk;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseUnauthorized;
import ru.t1debut.itcamp.consent.api.dto.consent.CheckConsentLinkAccessResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.ConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.GetConsentRequest;
import ru.t1debut.itcamp.consent.api.dto.consent.GetConsentRequestEmailMessageStatusResponse;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentRequest;
import ru.t1debut.itcamp.consent.api.dto.consent.SendConsentResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.create.CreateCandidateProfileRequest;
import ru.t1debut.itcamp.consent.model.consent.ConsentStatus;

import java.util.List;
import java.util.UUID;

import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_CHECK_LINK_ACCESS_TAG;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_GET_CONSENT_REQUEST_EMAIL_MESSAGE_STATUS_TAG;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_GET_CONSENT_TAG;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_SEND_SOPD_TAG;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_SET_CONSENT_STATUS_AND_SAVE_CANDIDATE_PROFILE_TAG;

@ApiResponseOk
@ApiResponseInternalServerError
@ApiResponseIllegalDtoValue
public interface ConsentControllerSpec {

    @ApiResponseInvalidRequest
    @Operation(summary = SWAGGER_CHECK_LINK_ACCESS_TAG)
    ResponseEntity<CheckConsentLinkAccessResponse> checkLinkAccess(
            @PathVariable @Valid @NotNull UUID consentId
    );

    @ApiResponseUnauthorized
    @ApiResponseNotFound
    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    @Operation(summary = SWAGGER_GET_CONSENT_REQUEST_EMAIL_MESSAGE_STATUS_TAG)
    ResponseEntity<GetConsentRequestEmailMessageStatusResponse> getConsentRequestEmailMessageStatus(
            @PathVariable @Valid @NotNull UUID consentId
    );

    @ApiResponseUnauthorized
    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    @Operation(summary = SWAGGER_GET_CONSENT_TAG)
    ResponseEntity<List<ConsentResponse>> getConsents(@RequestBody @Valid GetConsentRequest getConsentRequest);

    @ApiResponseUnauthorized
    @ApiResponseAlreadyExist
    @ApiResponseIllegalValue
    @ApiResponseNotFound
    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    @Operation(summary = SWAGGER_SEND_SOPD_TAG)
    ResponseEntity<SendConsentResponse> sendSopdToCandidate(
            @RequestBody @Valid SendConsentRequest sendConsentRequest
    );

    @ApiResponseIllegalValue
    @ApiResponseNotFound
    @Operation(summary = SWAGGER_SET_CONSENT_STATUS_AND_SAVE_CANDIDATE_PROFILE_TAG)
    ResponseEntity<Void> setConsentStatusAndSaveCandidateProfile(
            @PathVariable @Valid @NotNull UUID consentId,
            @PathVariable @Valid @NotNull ConsentStatus consentStatus,
            @RequestBody @Valid CreateCandidateProfileRequest createCandidateProfileRequest
    );
}
