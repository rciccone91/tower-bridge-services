package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.response.AlumnoResponse;
import com.houndsoft.towerbridge.services.service.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {

    @Autowired
    ReportesService reportesService;

    @GetMapping("/examen-internacional-2")
    public ResponseEntity<Map<String, Object>> getExamenInternacionalReport(@RequestParam(defaultValue = "0") int page) {
        try {
            Pageable paging = PageRequest.of(page, 15, Sort.by(Sort.Direction.ASC, "id"));
            Page<AlumnoResponse> pageAlumno = reportesService.findAlumnosByExamenInternacionalPaged(paging);
            List<AlumnoResponse> alumnos = pageAlumno.getContent();

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

    @GetMapping("/examen-internacional")
    public ResponseEntity<List<AlumnoResponse>> getExamenInternacionalReport() {
        try {
            List<AlumnoResponse> alumnos =  reportesService.findAlumnosByExamenInternacional();
            return new ResponseEntity<>(alumnos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mes-adeudado")
    public ResponseEntity<List<Map<String, Object>>> getMesCorrienteAdeudadoReport() {
        try {
            final List<Map<String, Object>> alumnosByMesAdeudado = reportesService.findAlumnosByMesAdeudado();
            return new ResponseEntity<>(alumnosByMesAdeudado,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mal-desempenio")
    public ResponseEntity<List<Map<String, Object>>> getAlumnosConMalDesempenioReport() {
        try {
            final List<Map<String, Object>> alumnosConMalDesempenio = reportesService.findAlumnosByMalDesempenio();
            return new ResponseEntity<>(alumnosConMalDesempenio,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pagos-del-mes")
    public ResponseEntity<List<Map<String, Object>>> getPagosProveedoresDelMesReport() {
        try {
            final List<Map<String, Object>> pagosProveedoresDelMes = reportesService.findPagosProveedoresDelMes();
            return new ResponseEntity<>(pagosProveedoresDelMes,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movimientos-manuales-del-mes")
    public ResponseEntity<List<Map<String, Object>>> getMovimientosManualesDelMes() {
        try {
            final List<Map<String, Object>> movimientosManualesDelMes = reportesService.findMovimientosManualesDelMes();
            return new ResponseEntity<>(movimientosManualesDelMes,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clases-cursos-especificos")
    public ResponseEntity<List<Map<String, Object>>> getClasesDeCursosEspecificos() {
        try {
            final List<Map<String, Object>> clasesDeCursosEspecificos = reportesService.findClasesDeCursosEspecificos();
            return new ResponseEntity<>(clasesDeCursosEspecificos,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
