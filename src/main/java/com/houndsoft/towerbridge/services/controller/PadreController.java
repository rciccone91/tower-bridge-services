package com.houndsoft.towerbridge.services.controller;


import com.houndsoft.towerbridge.services.model.Padre;
import com.houndsoft.towerbridge.services.repository.PadreRepository;
import com.houndsoft.towerbridge.services.service.PadreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PadreController {
    @Autowired
    PadreService padreService;

    @Autowired
    PadreRepository padreRepository;

    @GetMapping("/padres")
    public ResponseEntity<Map<String, Object>> getPaginatedPadres(@RequestParam(required = false) String nombreApellido,
                                                                  @RequestParam(required = false) String alumno,
                                                                          @RequestParam(defaultValue = "0") int page) {
        try {
            List<Padre> padres;
            Pageable paging = PageRequest.of(page, 8, Sort.by(Sort.Direction.ASC,"id"));

            Page<Padre> pagePadre;
            if(nombreApellido!= null){
                pagePadre = padreService.findByNombreApellidoContaining(nombreApellido,paging);
            } else if(alumno!= null){
                pagePadre = padreService.findByAlumnosNombreApellidoContaining(alumno,paging);
            } else{
                pagePadre = padreService.getPaginatedPadre(paging);
            }

            padres = pagePadre.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("padres", padres);
            response.put("currentPage", pagePadre.getNumber());
            response.put("totalItems", pagePadre.getTotalElements());
            response.put("totalPages", pagePadre.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/profesores/{id}")
//    public ResponseEntity<PadreResponse> getPadreById(@PathVariable("id") Long id) {
////        final Padre byId = padreService.getById(id);
////        final PadreResponse padreResponse = PadreResponse.buildFromPadre(byId);
//        return ResponseEntity.ok(padreResponse);
//    }
//
//    @PostMapping("/profesores")
//    public ResponseEntity<Padre> createPadre(@Valid @RequestBody PadreDTO profesorDTO) {
//        Padre padre = padreService.createPadre(profesorDTO);
//        return ResponseEntity.status(201).body(profesor);
//    }
//
//    @PatchMapping("/profesores/{id}")
//    public ResponseEntity<Padre> updatePadre(@PathVariable("id") Long id, @RequestBody PadreDTO profesorDTO) {
//        Padre profesor = padreService.upadePadre(id,profesorDTO);
//        return ResponseEntity.ok(profesor);
//    }
//
//    @DeleteMapping("/profesores/{id}")
//    public ResponseEntity<HttpStatus> deletePadre(@PathVariable("id") long id) {
//        padreService.softDeletePadre(id);
//        return ResponseEntity.noContent().build();
//    }
}
