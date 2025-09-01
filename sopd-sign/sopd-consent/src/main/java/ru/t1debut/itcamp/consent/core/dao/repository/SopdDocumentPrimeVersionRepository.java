package ru.t1debut.itcamp.consent.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1debut.itcamp.consent.model.DocumentsPrimeVersion;

@Repository
public interface SopdDocumentPrimeVersionRepository extends JpaRepository<DocumentsPrimeVersion, Integer> {

}
