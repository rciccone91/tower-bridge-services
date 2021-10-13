package com.houndsoft.towerbridge.services.controller;


import com.houndsoft.towerbridge.services.model.Padre;
import com.houndsoft.towerbridge.services.repository.PadreRepository;
import com.houndsoft.towerbridge.services.request.PadreDTO;
import com.houndsoft.towerbridge.services.response.PadreResponse;
import com.houndsoft.towerbridge.services.response.PadreSimpleResponse;
import com.houndsoft.towerbridge.services.service.PadreService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PadreController {

    @Autowired
    PadreService padreService;

    @GetMapping("/all-padres")
    public ResponseEntity<List<PadreSimpleResponse>> getAllPadres(){
        final List<PadreSimpleResponse> allPadres = padreService.getAllPadres().stream().map(PadreSimpleResponse::buildFromPadre).collect(Collectors.toList());
        return new ResponseEntity<>(allPadres,HttpStatus.OK);
    }

    @GetMapping("/padres")
    public ResponseEntity<Map<String, Object>> getPaginatedPadres(@RequestParam(required = false) String nombreApellido,
                                                                  @RequestParam(required = false) String alumno,
                                                                  @RequestParam(defaultValue = "0") int page) {
        try {
            List<Padre> padres;
            Pageable paging = PageRequest.of(page, 8, Sort.by(Sort.Direction.ASC, "id"));

            Page<Padre> pagePadre;
            if (nombreApellido != null) {
                pagePadre = padreService.findByNombreApellidoContaining(nombreApellido, paging);
            } else if (alumno != null) {
                pagePadre = padreService.findByAlumnosNombreApellidoContaining(alumno, paging);
            } else {
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

    @GetMapping("/padres/{id}")
    public ResponseEntity<PadreResponse> getPadreById(@PathVariable("id") Long id) {
        final PadreResponse padreResponse = padreService.getPadreDetail(id);
        return ResponseEntity.ok(padreResponse);
    }

    @PostMapping("/padres")
    public ResponseEntity<Padre> createPadre(@Valid @RequestBody PadreDTO padreDto) {
        Padre padre = padreService.createPadre(padreDto);
        return ResponseEntity.status(201).body(padre);
    }

    @PatchMapping("/padres/{id}")
    public ResponseEntity<Padre> updatePadre(@PathVariable("id") Long id, @RequestBody PadreDTO padreDTO) {
        Padre padre = padreService.upadePadre(id, padreDTO);
        return ResponseEntity.ok(padre);
    }

    @DeleteMapping("/padres/{id}")
    public ResponseEntity<HttpStatus> deletePadre(@PathVariable("id") long id) {
        padreService.softDeletePadre(id);
        return ResponseEntity.noContent().build();
    }
}
