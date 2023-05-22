package app.manguito.backend.repositories.impl;

import app.manguito.backend.dto.FilterEmprendimientosDTO;
import app.manguito.backend.entities.Emprendimiento;
import app.manguito.backend.repositories.CustomEmprendimientoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class CustomEmprendimientoRepositoryImpl implements CustomEmprendimientoRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Page<Emprendimiento> findEmprendimientos(FilterEmprendimientosDTO filter) {
        CriteriaBuilder builder =  em.getCriteriaBuilder();
        CriteriaQuery<Emprendimiento> criteria = builder.createQuery(Emprendimiento.class);
        Root<Emprendimiento> root = criteria.from(Emprendimiento.class);

        criteria.where(builder.and(filter.buildPredicates(builder, root))).distinct(true);

        List<Emprendimiento> result = em.createQuery(criteria).setFirstResult(filter.getPage() * 12).setMaxResults(12).getResultList();

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Emprendimiento> empRootCount = countQuery.from(Emprendimiento.class);
        countQuery.select(builder.count(empRootCount)).where(builder.and(filter.buildPredicates(builder, empRootCount))).distinct(true);

        Long count = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(result, PageRequest.of(filter.getPage(), 12), count);
    }
}
