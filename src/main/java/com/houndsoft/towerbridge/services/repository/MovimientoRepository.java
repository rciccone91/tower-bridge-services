package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.model.Movimiento;
import com.houndsoft.towerbridge.services.model.Movimiento.TipoDeMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;

public interface MovimientoRepository
    extends JpaRepository<Movimiento, Long>, JpaSpecificationExecutor<Movimiento> {

  Movimiento findByAlumnoEqualsAndCursoEqualsAndMesAbonadoEqualsAndActivoTrue(
      Alumno alumno, Curso curso, YearMonth yearMonth);

  @Query(
      value = "select * from movimientos order by  fecha_creacion desc limit(20);",
      nativeQuery = true)
  List<Movimiento> findTopOrderByFechaCreacionDesc();

  List<Movimiento> findByFechaBetweenAndActivoTrue(Date fechaDesde, Date fechaHasta);

  Page<Movimiento> findByTipoMovimientoEqualsAndActivoTrue(TipoDeMovimiento tipoMovimiento, Pageable paging);
}
