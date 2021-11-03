package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.model.intermediate.MesAdeudado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, Long>, JpaSpecificationExecutor<Alumno> {

    Page<Alumno> findByNombreApellidoContainingIgnoreCaseAndActivoTrue(String nombreApellido, Pageable paging);

    Page<Alumno> findByAnioEscolarContainingIgnoreCaseAndActivoTrue(String anioEscolar, Pageable paging);

    Page<Alumno> findByRindeExamenTrueAndActivoTrue(Pageable paging);

    List<Alumno> findByRindeExamenTrueAndActivoTrue();

    @Query(value = "select a.id, c.id, c.valor_arancel from alumnos as a\n" +
            "inner join alumnos_x_clase as axc\n" +
            "on a.id = axc.alumno_id\n" +
            "inner join clases as cl on axc.clase_id = cl.id\n" +
            "inner join cursos as c on cl.curso_id = c.id", nativeQuery = true)
    List<MesAdeudado> findByMesCorrienteAdeudado();

    Alumno findAlumnoByUsuarioEqualsAndActivoTrue(Usuario usuario);

}
