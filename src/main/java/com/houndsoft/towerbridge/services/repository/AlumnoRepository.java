package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, Long>, JpaSpecificationExecutor<Alumno> {

    Page<Alumno> findByNombreApellidoContainingIgnoreCaseAndActivoTrue(String nombreApellido, Pageable paging);

    Page<Alumno> findByAnioEscolarContainingIgnoreCaseAndActivoTrue(String anioEscolar, Pageable paging);

    Page<Alumno> findByRindeExamenTrueAndActivoTrue(Pageable paging);

    List<Alumno> findByRindeExamenTrueAndActivoTrue();

    Alumno findAlumnoByUsuarioEqualsAndActivoTrue(Usuario usuario);

}
