package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.exception.PadreACargoDeAlumnosException;

import com.houndsoft.towerbridge.services.model.Padre;
import com.houndsoft.towerbridge.services.repository.ContactoRepository;
import com.houndsoft.towerbridge.services.repository.PadreRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.request.PadreDTO;
import com.houndsoft.towerbridge.services.response.PadreResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class PadreService implements CommonFilter {

    @Autowired
    PadreRepository padreRepository;

    @Autowired
    ContactoRepository contactoRepository;

    public Page<Padre> getPaginatedPadre(Pageable pageable) {
        return padreRepository.findAll(isActive(),pageable);
    }

    public Page<Padre> findByNombreApellidoContaining(String nombreApellido, Pageable pageable) {
        return padreRepository.findByNombreApellidoContainingIgnoreCaseAndActivoTrue(nombreApellido,pageable);
    }


    public Page<Padre> findByAlumnosNombreApellidoContaining(String alumno, Pageable pageable) {
        //TODO - add activo
        return padreRepository.findByAlumnosNombreApellidoContainingIgnoreCase(alumno,pageable);
    }

    public Padre createPadre(PadreDTO padreDTO) {
        Padre padre = padreDTO.buildPadre();
        padreRepository.save(padre);
        return padre;
    }

    public Padre upadePadre(Long id, PadreDTO padreDTO) {
        final Padre retrievedPadre = padreRepository.getById(id);
        if (retrievedPadre.isPersisted()) {
            Long contactoId = retrievedPadre.getContacto().getId();
            Padre padre = padreDTO.buildPadre();
            padre.setId(id);
            padre.getContacto().setId(contactoId);
            padreRepository.save(padre);
            return padre;
        } else throw new RuntimeException("El padre no existe.");
    }

    public PadreResponse getPadreDetail(Long id) {
        final Padre padre = padreRepository.getById(id);
        return PadreResponse.buildFromPadre(padre);
    }

    public void softDeletePadre(long id) {
        final Padre retrievedPadre = padreRepository.getById(id);
        if (retrievedPadre.isPersisted()) {
            if (!retrievedPadre.getAlumnos().isEmpty()) {
                throw new PadreACargoDeAlumnosException(retrievedPadre.getNombreApellido());
            }
            retrievedPadre.setActivo(false);
            padreRepository.save(retrievedPadre);
        } else throw new RuntimeException("El profesor no existe.");
    }
}
