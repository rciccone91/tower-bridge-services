package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.model.Contacto;
import com.houndsoft.towerbridge.services.model.Profesor;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.repository.ContactoRepository;
import com.houndsoft.towerbridge.services.repository.ProfesorRepository;
import com.houndsoft.towerbridge.services.request.ProfesorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class ProfesorService implements CommonFilter {

    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    ContactoRepository contactoRepository;

    public List<Profesor> getAllActive() {
        return profesorRepository.findAll(isActive());
    }

    public Profesor getById(long id) {
            final Profesor byId = profesorRepository.getById(id);
            if(byId.isPersisted()){
                return byId;
            } else throw new RuntimeException("El profesor no existe.") ;
    }

    public Profesor createProfesor(ProfesorDTO profesorDTO) {
        Profesor profesor = profesorDTO.buildProfesor();
        profesorRepository.save(profesor);
        return profesor;
    }

    public Profesor upadeProfesor(long id, ProfesorDTO profesorDTO) {
            final Profesor byId = profesorRepository.getById(id);
            if(byId.isPersisted()){
                Contacto contacto = byId.getContacto();
                Profesor profesor = profesorDTO.buildProfesor();
                profesor.setId(id);
                if(contacto.equals(profesor.getContacto())){
                    profesor.setContacto(contacto);
                }
                profesorRepository.save(profesor);
                return profesor;
            } else throw new RuntimeException("El profesor no existe.") ;
    }
}

