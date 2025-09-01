package ru.t1debut.itcamp.consent.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1debut.itcamp.consent.model.SopdDocument;

import java.util.Optional;

@Repository
public interface SopdDocumentRepository extends JpaRepository<SopdDocument, Integer> {

    Optional<SopdDocument> findSopdDocumentByVersion(int version);
}
