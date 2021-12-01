package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Clase.Dia;
import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.model.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedNativeQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


public interface ClaseRepository extends JpaRepository<Clase, Long>, JpaSpecificationExecutor<Clase> {

    Page<Clase> findByDiaAndActivoTrue(Dia dia, Pageable pageable);

    Page<Clase> findByProfesorNombreApellidoContainingIgnoreCaseAndActivoTrue(String profesor, Pageable paging);

    Page<Clase> findByCursoNombreContainingIgnoreCaseAndActivoTrue(String curso, Pageable paging);

    Page<Clase> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre, Pageable paging);

    Page<Clase> findByProfesorEqualsAndActivoTrue(Profesor profesor, Pageable paging);

    Page<Clase> findByAlumnosAnotadosContainsAndActivoTrue(Alumno alumno, Pageable paging);

    List<Clase> findByCursoEqualsAndActivoTrue(Curso curso);

  @Query(
      value =
          "select * from clases as c where c.dia=?1 and " +
                  "(((c.horario_inicio>?2 and c.horario_inicio<?3) or (c.horario_fin>?2 and c.horario_fin<?3)) " +
                  "or (c.horario_inicio=?2 and c.horario_fin=?3));",
      nativeQuery = true)
  List<Clase> getClasesForDiaAndRangoHorario(String dia, Integer inicio, Integer fin);

    @Query(
            value =
                    "select * from clases as c where c.dia=?1 and profesor_id=?4  " +
                            "and (((c.horario_inicio>?2 and c.horario_inicio<?3) or (c.horario_fin>?2 and c.horario_fin<?3)) " +
                            "or (c.horario_inicio=?2 and c.horario_fin=?3));",
            nativeQuery = true)
    List<Clase> getClasesForProfesorAndDiaAndRangoHorario(String dia, Integer inicio, Integer fin, Long profesorId);


    @Query(
            value =
                    "select * from clases as c " +
                            "inner join alumnos_x_clase as axc on axc.clase_id = c.id " +
                            "where axc.alumno_id=?4 " +
                            "and c.dia=?1 " +
                            "and (((c.horario_inicio>?2 and c.horario_inicio<?3) or (c.horario_fin>?2 and c.horario_fin<?3)) " +
                            "or (c.horario_inicio=?2 and c.horario_fin=?3));",
            nativeQuery = true)
    List<Clase> getClasesForAlumnoAndDiaAndRangoHorario(String dia, Integer inicio, Integer fin, Long profesorId);

}
