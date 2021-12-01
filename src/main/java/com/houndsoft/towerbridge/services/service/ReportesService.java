package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.repository.AlumnoRepository;
import com.houndsoft.towerbridge.services.dao.ReportesDao;
import com.houndsoft.towerbridge.services.response.AlumnoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class ReportesService {

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    MovimientoService movimientoService;

    @Autowired
    ReportesDao reportesDao;

    public Page<AlumnoResponse> findAlumnosByExamenInternacionalPaged(Pageable paging) {
        return alumnoRepository.findByRindeExamenTrueAndActivoTrue(paging).map(AlumnoResponse::buildFromAlumno);
    }

    public List<AlumnoResponse> findAlumnosByExamenInternacional() {
        return alumnoRepository.findByRindeExamenTrueAndActivoTrue().stream().map(AlumnoResponse::buildFromAlumno).collect(Collectors.toList());
    }

//    public List<Map<String, Object>> findAlumnosByMesAdeudado(){
//        return reportesDao.getMesActualAdeudado();
//    }

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

    public List<Map<String, Object>> getTresMesesAdeudadosReport() {
        final List<Map<String, Object>> tresMesesAdeudados = new ArrayList<>();
        alumnoRepository.findAll(isActive()).forEach(a -> {
            final List<Map<String, Object>> estado = movimientoService.getEstadoDeCuentaPorAlumno(a);
            final List<Map<String, Object>> adeudados = estado.stream().filter(m -> m.get("estado").equals("Adeudado")).collect(Collectors.toList());
            if(adeudados.size() >=3){
                Map<String, Object> map = new HashMap<>();
                map.put("nombre",a.getNombreApellido());
                map.put("dni",a.getDni());
                StringBuilder stringBuilder  = new StringBuilder();
                adeudados.forEach(m -> stringBuilder.append(m.get("arancel")).append(" / "));
                map.put("mesesAdeudados", stringBuilder.toString());
                tresMesesAdeudados.add(map);
            }
        });
        return tresMesesAdeudados;
    }

    public List<Map<String, Object>> findClasesPorDiaYHorario(Clase.Dia dia, Integer horarioInicio, Integer horarioFin) {
        return reportesDao.getClasesPorDiaYHorario(dia,horarioInicio,horarioFin);
    }

    public List<Map<String, Object>> findAlumnosByMesAdeudado() {
        final List<Map<String, Object>> mesActualAdeudado = new ArrayList<>();
        alumnoRepository.findAll(isActive()).forEach(a -> {
            final List<Map<String, Object>> estado = movimientoService.getEstadoMesCorrientePorAlumno(a);
            final List<Map<String, Object>> adeudados = estado.stream().filter(m -> m.get("estado").equals("Adeudado")).collect(Collectors.toList());
            adeudados.forEach(ad -> {
                Map<String, Object> map = new HashMap<>();
                map.put("nombreApellido",a.getNombreApellido());
                map.put("dni",a.getDni());
                map.put("curso",ad.get("curso"));
                map.put("arancel",ad.get("valor"));
                map.put("recargo",ad.get("recargo"));
                map.put("clase",ad.get("clase"));
                mesActualAdeudado.add(map);
            });
        });
        return mesActualAdeudado;
//        final Map<String, Object> map = new HashMap<>();
//        map.put("nombreApellido",rs.getString("nombre_apellido"));
//        map.put("dni",rs.getInt("dni"));
//        map.put("curso",rs.getString("curso"));
//        map.put("clase",rs.getString("clase"));
//        map.put("arancel",rs.getInt("valor_arancel"));
//        map.put("recargo",rs.getInt("recargo"));
//        return null;
    }
}
