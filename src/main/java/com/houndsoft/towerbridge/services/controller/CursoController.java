package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Map<String, Object>>> getAllCursosForClases() {
        List<Map<String,Object>> response = new ArrayList<>();
        cursoService.getAllActive().forEach(curso -> {
            Map<String,Object> map = new HashMap<>();
            map.put("id",curso.getId());
            map.put("nombre",curso.getNombre());
            response.add(map);
        });
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cursos/valor-examen")
    public ResponseEntity<List<Map<String, Object>>> getAllCursos() {
        final List<Map<String,Object>> cursos = cursoService.getCursosValorExamen();
        return ResponseEntity.ok(cursos);
    }
}
