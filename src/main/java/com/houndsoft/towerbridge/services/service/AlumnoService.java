package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.exception.AlumnoInscriptoEnClaseException;
import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Padre;
import com.houndsoft.towerbridge.services.repository.AlumnoRepository;
import com.houndsoft.towerbridge.services.repository.ContactoRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.request.AlumnoDTO;
import com.houndsoft.towerbridge.services.response.AlumnoResponse;
import com.houndsoft.towerbridge.services.response.PadreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class AlumnoService  implements CommonFilter {

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    ContactoRepository contactoRepository;

    public AlumnoResponse getAlumnoDetail(Long id) {
        final Alumno alumno = alumnoRepository.getById(id);
        return AlumnoResponse.buildFromAlumno(alumno);
    }

    public Alumno createAlumno(AlumnoDTO alumnoDto) {
        Alumno alumno = alumnoDto.buildAlumno();
        alumnoRepository.save(alumno);
        return alumno;    }

    public Alumno upadeAlumno(Long id, AlumnoDTO alumnoDTO) {
        final Alumno retrievedAlumno = alumnoRepository.getById(id);
        if (retrievedAlumno.isPersisted()) {
            Long contactoId = retrievedAlumno.getContacto().getId();
            Alumno alumno = alumnoDTO.buildAlumno();
            alumno.setId(id);
            alumno.getContacto().setId(contactoId);
            alumnoRepository.save(alumno);
            return alumno;
        } else throw new RuntimeException("El alumno no existe.");
    }

    public void softDeleteAlumno(long id) {
        final Alumno retrievedAlumno = alumnoRepository.getById(id);
        if (retrievedAlumno.isPersisted()) {
            if (!retrievedAlumno.getClases().isEmpty()) {
                throw new AlumnoInscriptoEnClaseException(retrievedAlumno.getNombreApellido(),retrievedAlumno.getClases().get(0).getNombre());
            }
            retrievedAlumno.setActivo(false);
            alumnoRepository.save(retrievedAlumno);
        } else throw new RuntimeException("El alumno no existe.");

    }

    public Page<Alumno> findByNombreApellidoContaining(String nombreApellido, Pageable paging) {
        return alumnoRepository.findByNombreApellidoContainingIgnoreCaseAndActivoTrue(nombreApellido,paging);
    }

//    public Page<Alumno> findByEdadEquals(Integer edad, Pageable paging) {
//        return alumnoRepository.findByEdad(edad,paging);
//    }

    public Page<Alumno> findByAnioEscolarContaining(String anioEscolar, Pageable paging) {
        return alumnoRepository.findByAnioEscolarContainingIgnoreCaseAndActivoTrue(anioEscolar,paging);
    }

    public Page<Alumno> getPaginatedAlumno(Pageable paging) {
        return alumnoRepository.findAll(isActive(),paging);
    }
}
