package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.exception.ClasesExistentesParaCursoException;
import com.houndsoft.towerbridge.services.exception.UsuarioYaExistenteException;
import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.repository.ClaseRepository;
import com.houndsoft.towerbridge.services.repository.CursoRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.request.ClaseDTO;
import com.houndsoft.towerbridge.services.request.CursoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class CursoService implements CommonFilter {

  @Autowired CursoRepository cursoRepository;
  @Autowired ClaseRepository claseRepository;

  public List<Curso> getAllActive() {
    return cursoRepository.findAll(isActive());
  }

  public List<Map<String, Object>> getCursosValorExamen() {
    return cursoRepository.findAll(isActive()).stream()
        .map(
            c -> {
              Map<String, Object> map = new HashMap<>();
              map.put("nombre", c.getNombre());
              map.put("arancel", c.getValorArancel());
              map.put("valorExamen", c.getValorExamen());
              return map;
            })
        .filter(c -> c.get("valorExamen") != null)
        .collect(Collectors.toList());
  }

  public Page<Curso> findByNombreContaining(String nombre, Pageable paging) {
    return cursoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre, paging);
  }

  public Curso getCursoDetail(Long id) {
    final Curso curso = cursoRepository.getById(id);
    if (curso.isPersisted()) {
      if (curso.getValorExamen() == null) curso.setValorExamen(0);
      return curso;
    } else throw new RuntimeException("El curso no existe.");
  }

  public Page<Curso> findByTipoContaining(Curso.TipoDeCurso tipo, Pageable paging) {
    return cursoRepository.findByTipoDeCursoAndActivoTrue(tipo, paging);
  }

  public Page<Curso> getPaginatedCurso(Pageable paging) {
    return cursoRepository.findAll(isActive(), paging);
  }

  public Curso createCurso(CursoDTO cursoDTO) {
    Curso curso = cursoDTO.buildCurso();
    cursoRepository.save(curso);
    addLibros(curso, cursoDTO);
    cursoRepository.save(curso);
    return curso;
  }

  public Curso updateCurso(Long id, CursoDTO cursoDTO) {
    final Curso retrievedCurso = cursoRepository.getById(id);
    if (retrievedCurso.isPersisted()) {
      Curso curso = cursoDTO.buildCurso();
      curso.setId(id);
      addLibros(curso, cursoDTO);
      cursoRepository.save(curso);
      return curso;
    } else throw new RuntimeException("El curso no existe.");
  }

  private void addLibros(Curso curso, CursoDTO cursoDTO) {
    // TODO - add libros
  }

  public void softDeleteCurso(long id) {
    final Curso retrievedCurso = cursoRepository.getById(id);
    if (retrievedCurso.isPersisted()) {
      final List<Clase> clasesByCurso = claseRepository.findByCursoEqualsAndActivoTrue(retrievedCurso);
      if(!clasesByCurso.isEmpty()){
        throw new ClasesExistentesParaCursoException(retrievedCurso,clasesByCurso.get(0));
      }
      retrievedCurso.setActivo(false);
      cursoRepository.save(retrievedCurso);
    } else throw new RuntimeException("El curso no existe.");
  }
}
