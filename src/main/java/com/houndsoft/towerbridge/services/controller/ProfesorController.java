package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Profesor;
import com.houndsoft.towerbridge.services.request.ProfesorDTO;
import com.houndsoft.towerbridge.services.response.ProfesorResponse;
import com.houndsoft.towerbridge.services.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProfesorController {

  @Autowired ProfesorService profesorService;

  @GetMapping("/profesores")
  public ResponseEntity<List<Profesor>> getAllProfesores(
      @RequestParam(required = false) String nombre, @RequestParam(required = false) String curso) {
    List<Profesor> profesores;
    if (nombre != null) {
      profesores = profesorService.findByNombreContaining(nombre);
    } else if (curso != null) {
      profesores = profesorService.findByCursoNombreContaining(curso);
    } else {
      profesores = profesorService.getAllActive();
    }
    return ResponseEntity.ok(profesores);
  }

  @GetMapping("/profesores/a-asignar")
  public ResponseEntity<List<Map<String, Object>>> getAllProfesoresForClases() {
    List<Map<String, Object>> response =
        profesorService.getAllActive().stream()
            .map(
                profesor -> {
                  Map<String, Object> map = new HashMap<>();
                  map.put("id", profesor.getId());
                  map.put("nombre", profesor.getNombreApellido());
                  return map;
                })
            .collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/profesores/{id}")
  public ResponseEntity<ProfesorResponse> getProfesorById(@PathVariable("id") Long id) {
    final ProfesorResponse profesorDetail = profesorService.getProfesorDetail(id);
    return ResponseEntity.ok(profesorDetail);
  }

  @PostMapping("/profesores")
  public ResponseEntity<Profesor> createProfesor(@Valid @RequestBody ProfesorDTO profesorDTO) {
    Profesor profesor = profesorService.createProfesor(profesorDTO);
    return ResponseEntity.status(201).body(profesor);
  }

  @PatchMapping("/profesores/{id}")
  public ResponseEntity<Profesor> updateProfesor(
      @PathVariable("id") Long id, @RequestBody ProfesorDTO profesorDTO) {
    Profesor profesor = profesorService.upadeProfesor(id, profesorDTO);
    return ResponseEntity.ok(profesor);
  }

  @DeleteMapping("/profesores/{id}")
  public ResponseEntity<HttpStatus> deleteProfesor(@PathVariable("id") long id) {
    profesorService.softDeleteProfesor(id);
    return ResponseEntity.noContent().build();
  }
}
