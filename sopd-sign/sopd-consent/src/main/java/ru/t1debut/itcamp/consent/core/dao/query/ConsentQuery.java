package ru.t1debut.itcamp.consent.core.dao.query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.t1debut.itcamp.consent.model.consent.Consent;
import ru.t1debut.itcamp.consent.model.consent.ConsentStatus;

import java.util.List;
import java.util.UUID;

public final class ConsentQuery {

    public static List<Consent> getConsents(
            EntityManager em,
            UUID id,
            Integer managerId,
            ConsentStatus status,
            String candidateEmail
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Consent> criteriaQuery = cb.createQuery(Consent.class);

        Root<Consent> root = criteriaQuery.from(Consent.class);
        criteriaQuery = criteriaQuery.where(
                id == null ? cb.conjunction() : cb.equal(root.get("id"), id),
                managerId == null ? cb.conjunction() : cb.equal(root.get("managerId"), managerId),
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status),
                candidateEmail == null
                        ? cb.conjunction()
                        : cb.equal(root.get("candidateProfile").get("email"), candidateEmail)
        );
        return em.createQuery(criteriaQuery).getResultList();
    }
}
