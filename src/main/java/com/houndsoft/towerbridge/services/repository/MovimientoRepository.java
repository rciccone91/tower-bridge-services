package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.YearMonth;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long>, JpaSpecificationExecutor<Movimiento> {

    public Movimiento findByAlumnoEqualsAndCursoEqualsAndMesAbonadoEquals(Alumno alumno, Curso curso, YearMonth yearMonth);
}

