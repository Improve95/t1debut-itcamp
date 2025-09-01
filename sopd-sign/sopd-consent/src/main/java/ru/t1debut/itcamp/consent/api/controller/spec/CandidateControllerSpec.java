package ru.t1debut.itcamp.consent.api.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseExpired;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseIllegalDtoValue;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseIllegalValue;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseOk;
import ru.t1debut.itcamp.consent.api.controller.annotation.ApiResponseUnauthorized;
import ru.t1debut.itcamp.consent.api.dto.profile.get.CandidateProfileResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.get.GetCandidateProfilesRequest;

import java.util.List;

import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_GET_CANDIDATE_PROFILE_TAG;
import static ru.t1debut.itcamp.consent.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

@ApiResponseOk
@ApiResponseIllegalValue
@ApiResponseIllegalDtoValue
@ApiResponseUnauthorized
public interface CandidateControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    @Operation(summary = SWAGGER_GET_CANDIDATE_PROFILE_TAG)
    ResponseEntity<List<CandidateProfileResponse>> getCandidateProfile(
            @RequestBody @Valid GetCandidateProfilesRequest getCandidateProfilesRequest
    );
}
