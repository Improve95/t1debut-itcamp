package ru.t1debut.itcamp.consent.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1debut.itcamp.consent.api.controller.spec.CandidateControllerSpec;
import ru.t1debut.itcamp.consent.api.dto.profile.get.CandidateProfileResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.get.GetCandidateProfilesRequest;
import ru.t1debut.itcamp.consent.core.service.CandidateProfileService;

import java.util.List;

import static ru.t1debut.itcamp.consent.api.ApiPath.CANDIDATE_PROFILES;
import static ru.t1debut.itcamp.consent.api.ApiPath.PARAMETRIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping(CANDIDATE_PROFILES)
public class CandidateProfileController implements CandidateControllerSpec {

    private final CandidateProfileService candidateProfileService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping(PARAMETRIZED)
    public ResponseEntity<List<CandidateProfileResponse>> getCandidateProfile(
            @Valid GetCandidateProfilesRequest getCandidateProfilesRequest
    ) {
        var candidateProfilesResponse = candidateProfileService.findCandidateProfilesWithParameters(getCandidateProfilesRequest);
        return ResponseEntity.ok(candidateProfilesResponse);
    }
}
