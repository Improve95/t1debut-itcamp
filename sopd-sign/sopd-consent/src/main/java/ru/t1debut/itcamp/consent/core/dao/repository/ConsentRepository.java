package ru.t1debut.itcamp.consent.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1debut.itcamp.consent.model.consent.Consent;
import ru.t1debut.itcamp.consent.model.consent.ConsentStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, UUID> {

    List<Consent> findConsentByIdAndStatusAndCreatedAtAfter(UUID id, ConsentStatus status, Instant createdAtAfter);

    List<Consent> findConsentByStatusAndCreatedAtAfterAndCandidateProfile_Email(
            ConsentStatus status,
            Instant createdAtAfter,
            String candidateProfileEmail
    );

    List<Consent> findConsentByStatusAndCreatedAtAfter(ConsentStatus status, Instant createdAtAfter);
}
