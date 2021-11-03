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
    } else throw new RuntimeException("La clase no existe.");
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
    final Profesor claseProfesor = getClaseProfesor(claseDto);
    final List<Alumno> claseAlumnos = getClaseAlumnos(claseDto);

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

  private Profesor getClaseProfesor(ClaseDTO claseDto) {
    final Profesor profesor = profesorRepository.getById(claseDto.getProfesorId());
    if (profesor.isPersisted()) {
      return profesor;
    } else throw new RuntimeException("El profesor no existe.");
  }

  private List<Alumno> getClaseAlumnos(ClaseDTO claseDto) {
    List<Alumno> alumnos =
        claseDto.getAlumnosIds().stream()
            .map(a -> alumnoRepository.getById(a))
            .collect(Collectors.toList());
    List<Alumno> alumnosAnotados = new ArrayList<>();
    alumnos.forEach(
        alumno -> {
          if (alumno.isPersisted()) {
            alumnosAnotados.add(alumno);
          } else throw new RuntimeException("Ocurrió un error. Uno de los alumnos asignados para la clase no existe.");
        });
    return alumnosAnotados;
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

  public Page<Clase> findByProfesor(Long profesorId, Pageable paging) {
    return profesorRepository.findById(profesorId)
            .map(p -> claseRepository.findByProfesorEqualsAndActivoTrue(p,paging))
            .orElseThrow(() -> new RuntimeException("Error al buscar clases. El profesor por el cual se realizó la consulta no existe."));
  }

  public Page<Clase> findByAlumno(Long alumnoId, Pageable paging) {
    return alumnoRepository.findById(alumnoId)
            .map(a -> claseRepository.findByAlumnosAnotadosContainsAndActivoTrue(a,paging))
            .orElseThrow(() -> new RuntimeException("Error al buscar clases. El alumno por el cual se realizó la consulta no existe."));  }
}
