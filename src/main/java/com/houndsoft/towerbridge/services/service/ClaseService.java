package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.model.Profesor;
import com.houndsoft.towerbridge.services.repository.AlumnoRepository;
import com.houndsoft.towerbridge.services.repository.ClaseRepository;
import com.houndsoft.towerbridge.services.repository.CursoRepository;
import com.houndsoft.towerbridge.services.repository.ProfesorRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.request.ClaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.propertyEquals;
import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaseService implements CommonFilter {

  @Autowired ClaseRepository claseRepository;
  @Autowired CursoRepository cursoRepository;
  @Autowired ProfesorRepository profesorRepository;
  @Autowired AlumnoRepository alumnoRepository;

  public List<Clase> getClasesForProfesor(Profesor profesor) {
    return claseRepository.findAll(propertyEquals(profesor, "profesor"));
  }

  public void softDeleteClase(long id) {
    final Clase retrievedClase = claseRepository.getById(id);
    if (retrievedClase.isPersisted()) {
      retrievedClase.setActivo(false);
      claseRepository.save(retrievedClase);
    } else throw new RuntimeException("El padre no existe.");
  }

  public Clase updateClase(Long id, ClaseDTO claseDTO) {
    final Clase retrievedClase = claseRepository.getById(id);
    if (retrievedClase.isPersisted()) {
      Clase clase = claseDTO.buildClase();
      clase.setId(id);
      addCurso(clase, claseDTO);
      addAlumnos(clase, claseDTO);
      addProfesor(clase, claseDTO);
      claseRepository.save(clase);
      return clase;
    } else throw new RuntimeException("La clase no existe.");
  }

  public Clase createClase(ClaseDTO claseDto) {
    Clase clase = claseDto.buildClase();
    claseRepository.save(clase);
    addCurso(clase, claseDto);
    addAlumnos(clase, claseDto);
    addProfesor(clase, claseDto);
    claseRepository.save(clase);
    return clase;
  }

  private void addCurso(Clase clase, ClaseDTO claseDto) {
    final Curso retrievedCurso = cursoRepository.getById(claseDto.getCurso());
    if (retrievedCurso.isPersisted()) {
      clase.setCurso(retrievedCurso);
    } else throw new RuntimeException("El curso no existe.");
  }

  private void addProfesor(Clase clase, ClaseDTO claseDto) {
    final Profesor profesor = profesorRepository.getById(claseDto.getProfesorId());
    if (profesor.isPersisted()) {
      clase.setProfesor(profesor);
    } else throw new RuntimeException("El profesor no existe.");
  }

  private void addAlumnos(Clase clase, ClaseDTO claseDto) {
    List<Alumno> alumnos =
        claseDto.getAlumnosIds().stream()
            .map(a -> alumnoRepository.getById(a))
            .collect(Collectors.toList());
    alumnos.forEach(
        alumno -> {
          if (alumno.isPersisted()) {
            List<Alumno> alumnosAnotados = clase.getAlumnosAnotados() != null ? clase.getAlumnosAnotados() : new ArrayList<>();
            alumnosAnotados.add(alumno);
            clase.setAlumnosAnotados(alumnosAnotados);
          } else throw new RuntimeException("El profesor no existe.");
        });
  }

  public Clase getClaseDetail(Long id) {
    final Clase clase = claseRepository.getById(id);
    if (clase.isPersisted()) {
      return clase;
    } else throw new RuntimeException("La clase no existe.");
  }

  public Page<Clase> getPaginatedClase(Pageable paging) {
    return claseRepository.findAll(isActive(), paging);
  }

  public Page<Clase> findByDiaContaining(Clase.Dia dia, Pageable paging) {
    return claseRepository.findByDiaAndActivoTrue(dia, paging);
  }

  public Page<Clase> findByProfesorNombreApellidoContaining(String profesor, Pageable paging) {
    return claseRepository.findByProfesorNombreApellidoContainingIgnoreCaseAndActivoTrue(
        profesor, paging);
  }

  public Page<Clase> findByCursoNombreContaining(String curso, Pageable paging) {
    return claseRepository.findByCursoNombreContainingIgnoreCaseAndActivoTrue(curso, paging);
  }

  public Page<Clase> findByNombreContaining(String nombre, Pageable paging) {
    return claseRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre, paging);
  }
}
