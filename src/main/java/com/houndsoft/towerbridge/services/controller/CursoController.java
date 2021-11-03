package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.request.ClaseDTO;
import com.houndsoft.towerbridge.services.request.CursoDTO;
import com.houndsoft.towerbridge.services.response.ClaseResponse;
import com.houndsoft.towerbridge.services.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @GetMapping("/cursos/a-asignar")
    public ResponseEntity<List<Map<String, Object>>> getAllCursosForCursos() {
        List<Map<String,Object>> response = new ArrayList<>();
        cursoService.getAllActive().forEach(curso -> {
            Map<String,Object> map = new HashMap<>();
            map.put("id",curso.getId());
            map.put("nombre",curso.getNombre());
            response.add(map);
        });
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cursos")
    public ResponseEntity<Map<String, Object>> getPaginatedCursos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Curso.TipoDeCurso tipo,
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Curso> cursos;
            Pageable paging = PageRequest.of(page, 8, Sort.by(Sort.Direction.ASC, "id"));

            Page<Curso> pageCurso;
            if (nombre != null) {
                pageCurso = cursoService.findByNombreContaining(nombre, paging);
            } else if (tipo != null) {
                pageCurso = cursoService.findByTipoContaining(tipo, paging);
            } else {
                pageCurso = cursoService.getPaginatedCurso(paging);
            }

            cursos = pageCurso.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("cursos", cursos);
            response.put("currentPage", pageCurso.getNumber());
            response.put("totalItems", pageCurso.getTotalElements());
            response.put("totalPages", pageCurso.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable("id") Long id) {
        final Curso curso = cursoService.getCursoDetail(id);
        return ResponseEntity.ok(curso);
    }

    @PostMapping("/cursos")
    public ResponseEntity<Curso> createCurso(@Valid @RequestBody CursoDTO cursoDTO) {
        Curso curso = cursoService.createCurso(cursoDTO);
        return ResponseEntity.status(201).body(curso);
    }

    @PatchMapping("/cursos/{id}")
    public ResponseEntity<Curso> updateCurso(
            @PathVariable("id") Long id, @RequestBody CursoDTO cursoDTO) {
        Curso curso = cursoService.updateCurso(id, cursoDTO);
        return ResponseEntity.ok(curso);
    }

    @GetMapping("/cursos/valor-examen")
    public ResponseEntity<List<Map<String, Object>>> getAllCursos() {
        final List<Map<String,Object>> cursos = cursoService.getCursosValorExamen();
        return ResponseEntity.ok(cursos);
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<HttpStatus> deleteCurso(@PathVariable("id") long id) {
        cursoService.softDeleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}
