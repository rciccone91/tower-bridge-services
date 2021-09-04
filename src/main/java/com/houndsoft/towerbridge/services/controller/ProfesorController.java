package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Profesor;
import com.houndsoft.towerbridge.services.request.ProfesorDTO;
import com.houndsoft.towerbridge.services.service.ProfesorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.houndsoft.towerbridge.services.utils.CollectionUtils.iterableToList;

@RestController
@RequestMapping("/api")
public class ProfesorController {

    private static final Logger log = LoggerFactory.getLogger(ProfesorController.class);

    @Autowired
    ProfesorService profesorService;

    @GetMapping("/profesores")
    public ResponseEntity<List<Profesor>> getAllProfesores() {
            List<Profesor> profesores = iterableToList(profesorService.getAll());
            return ResponseEntity.ok(profesores);
    }

    @GetMapping("/profesores/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable("id") long id) {
        final Profesor byId = profesorService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping("/profesores")
    public ResponseEntity<Profesor> createProfesor(@Valid @RequestBody ProfesorDTO profesorDTO) {
            Profesor profesor = profesorService.createProfesor(profesorDTO);
            return ResponseEntity.ok(profesor);
    }

    @PatchMapping("/profesores/{id}")
    public ResponseEntity<Profesor> updateProfesor(@PathVariable("id") long id, @RequestBody ProfesorDTO profesorDTO) {
            Profesor profesor = profesorService.upadeProfesor(id,profesorDTO);
            return ResponseEntity.ok(profesor);
    }

    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<HttpStatus> deleteProfesor(@PathVariable("id") long id) {
        return null;
    }
}
