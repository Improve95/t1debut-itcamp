package ru.t1debut.itcamp.consent.core.service.implement;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.api.dto.profile.create.CreateCandidateProfileRequest;
import ru.t1debut.itcamp.consent.api.dto.profile.get.CandidateProfileResponse;
import ru.t1debut.itcamp.consent.api.dto.profile.get.GetCandidateProfilesRequest;
import ru.t1debut.itcamp.consent.api.exception.ServiceException;
import ru.t1debut.itcamp.consent.core.dao.query.CandidateProfileQuery;
import ru.t1debut.itcamp.consent.core.dao.repository.CandidateProfileRepository;
import ru.t1debut.itcamp.consent.core.service.CandidateProfileService;
import ru.t1debut.itcamp.consent.model.CandidateProfile;
import ru.t1debut.itcamp.consent.util.mapper.CandidateProfileMapper;

import java.util.List;
import java.util.Optional;

import static ru.t1debut.itcamp.consent.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CandidateProfileServiceImpl implements CandidateProfileService {

    private final CandidateProfileMapper candidateProfileMapper;

    private final CandidateProfileRepository candidateProfileRepository;

    private final EntityManager em;

    @Transactional
    @Override
    public List<CandidateProfileResponse> findCandidateProfilesWithParameters(
            GetCandidateProfilesRequest getCandidateProfilesRequest
    ) {
        return CandidateProfileQuery.getCandidateProfileQuery(
                        em,
                        getCandidateProfilesRequest.getId(),
                        getCandidateProfilesRequest.getEmail(),
                        getCandidateProfilesRequest.getPhone()
                ).stream()
                .map(candidateProfileMapper::toCandidateProfileResponse)
                .toList();
    }

    @Override
    public Optional<CandidateProfile> getOptionalCandidateProfile(String email) {
        return candidateProfileRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public CandidateProfile save(CandidateProfile candidateProfile) {
        return candidateProfileRepository.save(candidateProfile);
    }

    @Transactional
    @Override
    public CandidateProfileResponse patchCandidateProfile(
            CandidateProfile candidateProfile,
            CreateCandidateProfileRequest createCandidateProfileRequest
    ) {
        candidateProfileMapper.patchCandidateProfile(candidateProfile, createCandidateProfileRequest);
        return candidateProfileMapper.toCandidateProfileResponse(candidateProfile);
    }

    @Transactional
    @Override
    public CandidateProfile findCandidateProfile(String email) {
        return candidateProfileRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "candidateProfile", "email"));
    }
}
