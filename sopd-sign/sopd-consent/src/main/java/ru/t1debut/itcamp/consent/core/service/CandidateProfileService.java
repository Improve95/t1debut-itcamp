package ru.t1debut.itcamp.consent.core.service;

import ru.t1debut.itcamp.consent.api.dto.profile.create.CreateCandidateProfileRequest;
import ru.t1debut.itcamp.consent.api.dto.profile.get.CandidateProfileResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.get.GetCandidateProfilesRequest;
import ru.t1debut.itcamp.consent.model.CandidateProfile;

import java.util.List;
import java.util.Optional;

public interface CandidateProfileService {

    List<CandidateProfileResponse> findCandidateProfilesWithParameters(
            GetCandidateProfilesRequest getCandidateProfilesRequest
    );

    CandidateProfile save(CandidateProfile candidateProfile);

    CandidateProfileResponse patchCandidateProfile(
            CandidateProfile candidateProfile,
            CreateCandidateProfileRequest request
    );

    Optional<CandidateProfile> getOptionalCandidateProfile(String email);

    CandidateProfile findCandidateProfile(String email);
}
