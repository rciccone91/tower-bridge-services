package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.repository.AlumnoRepository;
import com.houndsoft.towerbridge.services.dao.ReportesDao;
import com.houndsoft.towerbridge.services.response.AlumnoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportesService {

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    ReportesDao reportesDao;

    public Page<AlumnoResponse> findAlumnosByExamenInternacionalPaged(Pageable paging) {
        return alumnoRepository.findByRindeExamenTrueAndActivoTrue(paging).map(AlumnoResponse::buildFromAlumno);
    }

    public List<AlumnoResponse> findAlumnosByExamenInternacional() {
        return alumnoRepository.findByRindeExamenTrueAndActivoTrue().stream().map(AlumnoResponse::buildFromAlumno).collect(Collectors.toList());
    }

    public List<Map<String, Object>> findAlumnosByMesAdeudado(){
        return reportesDao.getMesActualAdeudado();
    }

    public List<Map<String, Object>> findAlumnosByMalDesempenio(){
        return reportesDao.getAlumnosConMalDesempenio();
    }

    public List<Map<String, Object>> findPagosProveedoresDelMes() {
        return reportesDao.getPagosProveedoresDelMes();
    }

    public List<Map<String, Object>> findMovimientosManualesDelMes() {
        return reportesDao.getMovimientosManualesDelMes();
    }

    public List<Map<String, Object>> findClasesDeCursosEspecificos() {
        return reportesDao.getClasesDeCursosEspecificos();
    }
}
