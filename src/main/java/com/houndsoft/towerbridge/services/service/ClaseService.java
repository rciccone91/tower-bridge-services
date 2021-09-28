package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Profesor;
import com.houndsoft.towerbridge.services.repository.ClaseRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.propertyEquals;

import java.util.List;

@Service
public class ClaseService  implements CommonFilter {

    @Autowired
    ClaseRepository claseRepository;

    public List<Clase> getClasesForProfesor(Profesor profesor){
        return claseRepository.findAll(propertyEquals(profesor, "profesor"));
    }


}
