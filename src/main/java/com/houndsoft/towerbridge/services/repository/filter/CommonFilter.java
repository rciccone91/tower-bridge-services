package com.houndsoft.towerbridge.services.repository.filter;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface CommonFilter {

    static <T> Specification<T> isActive() {
        return (pr, cq, cb) -> isActiveFilter(cb, pr);
    }

    static <T> Predicate isActiveFilter(CriteriaBuilder cb, Root<T> pr) {
        return cb.equal(pr.get("activo"), true);
    }

    static <T> Specification<T> idEquals(Long id) {
        return (pr, cq, cb) -> cb.equal(pr.get("id"), id);
    }

    static <T, P> Specification<T> propertyEquals(P object, String property) {
        return (pr, cq, cb) -> {
            final Predicate equalId = cb.equal(pr.get(property), object);
            final Predicate active = isActiveFilter(cb, pr);
            return cb.and(active, equalId);
        };
    }
}
