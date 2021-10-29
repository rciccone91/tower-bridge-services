package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.request.ClaseDTO;
import com.houndsoft.towerbridge.services.response.ClaseResponse;
import com.houndsoft.towerbridge.services.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClaseController {

  @Autowired ClaseService claseService;

  @GetMapping("/clases")
  public ResponseEntity<Map<String, Object>> getPaginatedClases(
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) String curso,
      @RequestParam(required = false) String profesor,
      @RequestParam(required = false) Clase.Dia dia,
      @RequestParam(defaultValue = "0") int page) {
    try {
      List<Clase> clases;
      Pageable paging = PageRequest.of(page, 8, Sort.by(Sort.Direction.ASC, "id"));

      Page<Clase> pageClase;
      if (nombre != null) {
        pageClase = claseService.findByNombreContaining(nombre, paging);
      } else if (curso != null) {
        pageClase = claseService.findByCursoNombreContaining(curso, paging);
      } else if (profesor != null) {
        pageClase = claseService.findByProfesorNombreApellidoContaining(profesor, paging);
      } else if (dia != null) {
        pageClase = claseService.findByDiaContaining(dia, paging);
      } else {
        pageClase = claseService.getPaginatedClase(paging);
      }

      clases = pageClase.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("clases", clases);
      response.put("currentPage", pageClase.getNumber());
      response.put("totalItems", pageClase.getTotalElements());
      response.put("totalPages", pageClase.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/clases/{id}")
  public ResponseEntity<ClaseResponse> getClaseById(@PathVariable("id") Long id) {
    final ClaseResponse claseResponse = ClaseResponse.buildFromClase(claseService.getClaseDetail(id));
    return ResponseEntity.ok(claseResponse);
  }

  @PostMapping("/clases")
  public ResponseEntity<Clase> createClase(@Valid @RequestBody ClaseDTO claseDto) {

    // TODO - validate:
    // - que el alumno no tenga otra clase en ese dia-horario
    // - que el profesor no tenga otra clase en ese dia-horario
    // - que no haya mas de 3 clases en ese horario
    Clase clase = claseService.createClase(claseDto);
    return ResponseEntity.status(201).body(clase);
  }

  @PatchMapping("/clases/{id}")
  public ResponseEntity<Clase> updateClase(
      @PathVariable("id") Long id, @RequestBody ClaseDTO claseDTO) {
    Clase clase = claseService.updateClase(id, claseDTO);
    return ResponseEntity.ok(clase);
  }

  @DeleteMapping("/clases/{id}")
  public ResponseEntity<HttpStatus> deleteClase(@PathVariable("id") long id) {
    claseService.softDeleteClase(id);
    return ResponseEntity.noContent().build();
  }
}
