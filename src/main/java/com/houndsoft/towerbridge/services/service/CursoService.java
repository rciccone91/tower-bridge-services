package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.repository.CursoRepository;
import com.houndsoft.towerbridge.services.repository.PadreRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class CursoService implements CommonFilter {

  @Autowired CursoRepository cursoRepository;

  public List<Curso> getAllActive() {
    return cursoRepository.findAll(isActive());
  }

  public List<Map<String, Object>> getCursosValorExamen(){
    return cursoRepository.findAll(isActive()).stream().map(c ->
    {
      Map<String,Object> map = new HashMap<>();
      map.put("nombre",c.getNombre());
      map.put("arancel",c.getValorArancel());
      map.put("valorExamen",c.getValorExamen());
      return map;
    }).filter(c -> c.get("valorExamen") != null).collect(Collectors.toList());
  }
}
