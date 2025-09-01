package ru.t1debut.itcamp.consent.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1debut.itcamp.consent.model.EmailForm;

import java.util.Optional;

@Repository
public interface EmailFormRepository extends JpaRepository<EmailForm, Integer> {

    Optional<EmailForm> findEmailFormByVersion(int version);
}
