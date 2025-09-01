package ru.t1debut.itcamp.consent.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1debut.itcamp.consent.model.CandidateProfile;

import java.util.Optional;

@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Integer> {

    Optional<CandidateProfile> findByEmail(String email);
}
