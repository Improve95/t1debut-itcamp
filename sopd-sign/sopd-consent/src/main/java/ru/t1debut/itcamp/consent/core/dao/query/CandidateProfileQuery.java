package ru.t1debut.itcamp.consent.core.dao.query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.t1debut.itcamp.consent.model.CandidateProfile;

import java.util.List;

public final class CandidateProfileQuery {

    public static List<CandidateProfile> getCandidateProfileQuery(
            EntityManager em,
            Integer id,
            String email,
            String phone
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CandidateProfile> criteriaQuery = cb.createQuery(CandidateProfile.class);

        Root<CandidateProfile> root = criteriaQuery.from(CandidateProfile.class);
        criteriaQuery = criteriaQuery.where(cb.and(
                id == null ? cb.conjunction() : cb.equal(root.get("id"), id),
                email == null ? cb.conjunction() : cb.like(root.get("email"), email),
                phone == null ? cb.conjunction() : cb.equal(root.get("phone"), phone)
        ));
        return em.createQuery(criteriaQuery).getResultList();
    }
}
