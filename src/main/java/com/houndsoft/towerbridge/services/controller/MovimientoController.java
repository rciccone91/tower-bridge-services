package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Movimiento;
import com.houndsoft.towerbridge.services.request.MovimientoDTO;
import com.houndsoft.towerbridge.services.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class MovimientoController {

  @Autowired MovimientoService movimientoService;

  @PostMapping("/movimientos")
  public ResponseEntity<Movimiento> createMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO) {
    Movimiento movimiento = movimientoService.createMovimiento(movimientoDTO);
    return ResponseEntity.status(201).body(movimiento);
  }


  @DeleteMapping("/movimientos/{id}")
  public ResponseEntity<HttpStatus> deleteMovimiento(@PathVariable("id") long id) {
    movimientoService.softDeleteMovimiento(id);
    return ResponseEntity.noContent().build();
  }
}
