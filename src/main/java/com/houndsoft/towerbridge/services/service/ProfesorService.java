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

    public Profesor getById(Long id) {
        final Profesor byId = profesorRepository.getById(id);
        if (byId.isPersisted()) {
            return byId;
        } else throw new RuntimeException("El profesor no existe.");
    }

    public Profesor createProfesor(ProfesorDTO profesorDTO) {
        Profesor profesor = profesorDTO.buildProfesor();
        profesorRepository.save(profesor);
        return profesor;
    }

    public Profesor upadeProfesor(Long id, ProfesorDTO profesorDTO) {
        final Profesor retrievedProfesor = profesorRepository.getById(id);
        if (retrievedProfesor.isPersisted()) {
            Long contactoId = retrievedProfesor.getContacto().getId();
            Profesor profesor = profesorDTO.buildProfesor();
            profesor.setId(id);
            profesor.getContacto().setId(contactoId);
            profesorRepository.save(profesor);
            return profesor;
        } else throw new RuntimeException("El profesor no existe.");
    }

    public void softDeleteProfesor(Long id){
        final Profesor retrievedProfesor = profesorRepository.getById(id);
        if (retrievedProfesor.isPersisted()) {
            retrievedProfesor.setActivo(false);
            profesorRepository.save(retrievedProfesor);
        } else throw new RuntimeException("El profesor no existe.");
    }
}
