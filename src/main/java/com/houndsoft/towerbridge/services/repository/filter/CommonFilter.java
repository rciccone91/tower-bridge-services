package com.houndsoft.towerbridge.services.repository.filter;

import org.springframework.data.jpa.domain.Specification;

public interface CommonFilter {
    static <T> Specification<T> isActive() {
        return (pr, cq, cb) -> cb.equal(pr.get("activo"), true);
    }

    static <T> Specification<T> idEquals(Long id) {
        return (pr, cq, cb) -> cb.equal(pr.get("id"), id);
    }
}
