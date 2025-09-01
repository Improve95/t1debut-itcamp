package ru.t1debut.itcamp.consent.core.dao.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1debut.itcamp.consent.model.consent.Consent;
import ru.t1debut.itcamp.consent.model.message.ConsentRequestEmailMessage;

import java.util.UUID;

@Repository
public interface ConsentRequestEmailMessageRepository extends JpaRepository<ConsentRequestEmailMessage, UUID> {

    ConsentRequestEmailMessage findByConsent(Consent consent, Sort sort, Limit limit);
}
