package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Clase.Dia;
import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ClaseRepository extends JpaRepository<Clase, Long>, JpaSpecificationExecutor<Clase> {

    Page<Clase> findByDiaAndActivoTrue(Dia dia, Pageable pageable);

    Page<Clase> findByProfesorNombreApellidoContainingIgnoreCaseAndActivoTrue(String profesor, Pageable paging);

    Page<Clase> findByCursoNombreContainingIgnoreCaseAndActivoTrue(String curso, Pageable paging);

    Page<Clase> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre, Pageable paging);

    Page<Clase> findByProfesorEqualsAndActivoTrue(Profesor profesor, Pageable paging);

    Page<Clase> findByAlumnosAnotadosContainsAndActivoTrue(Alumno alumno, Pageable paging);
}
