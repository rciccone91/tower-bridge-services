package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.repository.ClaseRepository;
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
  @Autowired ClaseRepository claseRepository;

  @GetMapping("/clases")
  public ResponseEntity<Map<String, Object>> getPaginatedClases(
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) String curso,
      @RequestParam(required = false) String profesor,
      @RequestParam(required = false) Clase.Dia dia,
      @RequestParam(required = false) Long profesorId,
      @RequestParam(required = false) Long alumnoId,
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
      } else if (profesorId != null) {
        pageClase = claseService.findByProfesor(profesorId, paging);
      } else if (alumnoId != null) {
        pageClase = claseService.findByAlumno(alumnoId, paging);
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
    final ClaseResponse claseResponse =
        ClaseResponse.buildFromClase(claseService.getClaseDetail(id));
    return ResponseEntity.ok(claseResponse);
  }

  @PostMapping("/clases")
  public ResponseEntity<Clase> createClase(@Valid @RequestBody ClaseDTO claseDto) {
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

//  @GetMapping("/clases/query")
//  public ResponseEntity<List<Clase>> testQuery(@RequestParam("dia") Clase.Dia dia,@RequestParam("inicio") Integer inicio, @RequestParam("fin") Integer fin, @RequestParam("alum") Long alum) {
//    final List<Clase> clases = claseRepository.getClasesForAlumnoAndDiaAndRangoHorario(dia.toString(), inicio, fin, alum);
//    return ResponseEntity.ok(clases);
//  }
}
