package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.model.Padre;
import com.houndsoft.towerbridge.services.repository.ContactoRepository;
import com.houndsoft.towerbridge.services.repository.PadreRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
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

//    public PadrePaginableResponse getAllActive() {
//        final List<Padre> all = padreRepository.findAll(isActive());
//
//    }

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
}
