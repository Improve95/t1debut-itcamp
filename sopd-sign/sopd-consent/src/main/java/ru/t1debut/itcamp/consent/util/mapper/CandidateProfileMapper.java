package ru.t1debut.itcamp.consent.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.t1debut.itcamp.consent.api.dto.profile.create.CreateCandidateProfileRequest;
import ru.t1debut.itcamp.consent.api.dto.profile.create.CreateCandidateProfileResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.get.CandidateProfileResponse;
import ru.t1debut.itcamp.consent.model.CandidateProfile;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = MapperUtil.class
)
public interface CandidateProfileMapper {

    CreateCandidateProfileResponse toCreateCandidateProfileResponse(CandidateProfile candidateProfile);

    CandidateProfileResponse toCandidateProfileResponse(CandidateProfile candidateProfile);

    void patchCandidateProfile(
            @MappingTarget CandidateProfile candidateProfile,
            CreateCandidateProfileRequest createCandidateProfileRequest
    );
}
