package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Caja;
import com.houndsoft.towerbridge.services.model.Movimiento;
import com.houndsoft.towerbridge.services.model.Movimiento.TipoDeMovimiento;
import com.houndsoft.towerbridge.services.request.MovimientoDTO;
import com.houndsoft.towerbridge.services.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.houndsoft.towerbridge.services.utils.Utils.getFixedDate;

@RestController
@RequestMapping("/api")
public class MovimientoController {

  @Autowired MovimientoService movimientoService;

  @GetMapping("/caja/estado")
  public ResponseEntity<Map<String, Object>> getLastMovimientos() {
    final Map<String, Object> response = new HashMap<>();
    final Caja caja = movimientoService.getEstadoCaja();
    List<Movimiento> movimientos = movimientoService.getLastMovimientos();
    response.put("estadoCaja", caja);
    response.put("ultimosMovimientos", movimientos);
    return ResponseEntity.status(200).body(response);
  }

  @GetMapping("/movimientos/paginated")
  public ResponseEntity<Map<String, Object>> getPaginatedMovimientos(
          @RequestParam() String tipoMovimiento,
          @RequestParam(defaultValue = "0") int page) {
    try {
      List<Movimiento> movimientos;
      Pageable paging = PageRequest.of(page, 10);

      Page<Movimiento> pageMovimiento = movimientoService.getPaginatedByTipo(TipoDeMovimiento.valueOf(tipoMovimiento),paging);
      movimientos = pageMovimiento.getContent();
      Map<String, Object> response = new HashMap<>();
      response.put("movimientos", movimientos);
      response.put("currentPage", pageMovimiento.getNumber());
      response.put("totalItems", pageMovimiento.getTotalElements());
      response.put("totalPages", pageMovimiento.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/movimientos")
  public ResponseEntity<List<Movimiento>> getMovimientosEntreFechas(
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
    final List<Movimiento> movimientosEntreFechas =
        movimientoService.getMovimientosEntreFechas(getFixedDate(fechaDesde),getFixedDate(fechaHasta));
    return ResponseEntity.status(200).body(movimientosEntreFechas);
  }

  @PostMapping("/movimientos")
  public ResponseEntity<Movimiento> createMovimiento(
      @Valid @RequestBody MovimientoDTO movimientoDTO) {
    Movimiento movimiento = movimientoService.createMovimiento(movimientoDTO);
    return ResponseEntity.status(201).body(movimiento);
  }

  @DeleteMapping("/movimientos/{id}")
  public ResponseEntity<HttpStatus> deleteMovimiento(@PathVariable("id") long id) {
    movimientoService.softDeleteMovimiento(id);
    return ResponseEntity.noContent().build();
  }
}
