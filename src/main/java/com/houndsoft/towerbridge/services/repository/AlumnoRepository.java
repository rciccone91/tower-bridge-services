package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlumnoRepository extends JpaRepository<Alumno, Long>, JpaSpecificationExecutor<Alumno> {

    Page<Alumno> findByNombreApellidoContainingIgnoreCaseAndActivoTrue(String nombreApellido, Pageable paging);

    Page<Alumno> findByAnioEscolarContainingIgnoreCaseAndActivoTrue(String anioEscolar, Pageable paging);
}
