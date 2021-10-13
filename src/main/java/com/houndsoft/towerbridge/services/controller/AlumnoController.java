package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.request.AlumnoDTO;
import com.houndsoft.towerbridge.services.response.AlumnoResponse;
import com.houndsoft.towerbridge.services.service.AlumnoService;
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
public class AlumnoController {

    @Autowired
    AlumnoService alumnoService;

    @GetMapping("/alumnos")
    public ResponseEntity<Map<String, Object>> getPaginatedAlumnos(@RequestParam(required = false) String nombreApellido,
                                                                  @RequestParam(required = false) String anioEscolar,
                                                                  @RequestParam(defaultValue = "0") int page) {
        try {
            List<Alumno> alumnos;
            Pageable paging = PageRequest.of(page, 8, Sort.by(Sort.Direction.ASC, "id"));

            Page<Alumno> pageAlumno;
            if (nombreApellido != null) {
                pageAlumno = alumnoService.findByNombreApellidoContaining(nombreApellido, paging);
            } else if (anioEscolar != null){
                pageAlumno = alumnoService.findByAnioEscolarContaining(anioEscolar, paging);
            } else {
                pageAlumno = alumnoService.getPaginatedAlumno(paging);
            }

            alumnos = pageAlumno.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("alumnos", alumnos);
            response.put("currentPage", pageAlumno.getNumber());
            response.put("totalItems", pageAlumno.getTotalElements());
            response.put("totalPages", pageAlumno.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/alumnos/{id}")
    public ResponseEntity<AlumnoResponse> getAlumnoById(@PathVariable("id") Long id) {
        final AlumnoResponse alumnoResponse = alumnoService.getAlumnoDetail(id);
        return ResponseEntity.ok(alumnoResponse);
    }

    @PostMapping("/alumnos")
    public ResponseEntity<Alumno> createAlumno(@Valid @RequestBody AlumnoDTO alumnoDto) {
        Alumno alumno = alumnoService.createAlumno(alumnoDto);
        return ResponseEntity.status(201).body(alumno);
    }

    @PatchMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable("id") Long id, @RequestBody AlumnoDTO alumnoDTO) {
        Alumno alumno = alumnoService.upadeAlumno(id, alumnoDTO);
        return ResponseEntity.ok(alumno);
    }

    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity<HttpStatus> deleteAlumno(@PathVariable("id") long id) {
        alumnoService.softDeleteAlumno(id);
        return ResponseEntity.noContent().build();
    }
}
