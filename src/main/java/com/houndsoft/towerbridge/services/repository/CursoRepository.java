package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CursoRepository extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {

    Page<Curso> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre, Pageable paging);

    Page<Curso> findByTipoDeCursoAndActivoTrue(Curso.TipoDeCurso tipo, Pageable paging);
}
