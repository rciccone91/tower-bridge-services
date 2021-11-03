package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.exception.ProfesorAsignadoAClaseException;
import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Profesor;
import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.repository.ClaseRepository;
import com.houndsoft.towerbridge.services.repository.UsuarioRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.repository.ContactoRepository;
import com.houndsoft.towerbridge.services.repository.ProfesorRepository;
import com.houndsoft.towerbridge.services.request.ProfesorDTO;
import com.houndsoft.towerbridge.services.response.ProfesorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;
import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.propertyEquals;

@Service
public class ProfesorService implements CommonFilter {

  @Autowired ProfesorRepository profesorRepository;
  @Autowired ContactoRepository contactoRepository;
  @Autowired ClaseRepository claseRepository;
  @Autowired UsuarioRepository usuarioRepository;

  public List<Profesor> getAllActive() {
    return profesorRepository.findAll(isActive());
  }

  public Profesor createProfesor(ProfesorDTO profesorDTO) {
    Profesor profesor = profesorDTO.buildProfesor();
    final Optional<Usuario> usuario = getUsuario(profesorDTO);
    profesorRepository.save(profesor);
    usuario.ifPresent(profesor::setUsuario);
    profesorRepository.save(profesor);
    return profesor;
  }

  public Profesor upadeProfesor(Long id, ProfesorDTO profesorDTO) {
    final Profesor retrievedProfesor = profesorRepository.getById(id);
    if (retrievedProfesor.isPersisted()) {
      final Optional<Usuario> usuario = getUsuario(profesorDTO);
      Long contactoId = retrievedProfesor.getContacto().getId();
      Profesor profesor = profesorDTO.buildProfesor();
      profesor.setId(id);
      profesor.getContacto().setId(contactoId);
      usuario.ifPresent(profesor::setUsuario);
      profesorRepository.save(profesor);
      return profesor;
    } else throw new RuntimeException("El profesor no existe.");
  }

  private Optional<Usuario> getUsuario(ProfesorDTO profesorDTO) {
    if (profesorDTO.getUsuarioId() != null) {
      return usuarioRepository.findById(profesorDTO.getUsuarioId());
    } else return Optional.empty();
  }

  public void softDeleteProfesor(Long id) {
    final Profesor retrievedProfesor = profesorRepository.getById(id);
    if (retrievedProfesor.isPersisted()) {
      final List<Clase> profesorClases =
          claseRepository.findAll(propertyEquals(retrievedProfesor, "profesor"));
      if (!profesorClases.isEmpty()) {
        throw new ProfesorAsignadoAClaseException(retrievedProfesor.getNombreApellido());
      }
      retrievedProfesor.setActivo(false);
      profesorRepository.save(retrievedProfesor);
    } else throw new RuntimeException("El profesor no existe.");
  }

  public ProfesorResponse getProfesorDetail(Long id) {
    final Profesor profesor = profesorRepository.getById(id);
    final List<String> clases =
        claseRepository.findAll(propertyEquals(profesor, "profesor")).stream()
            .map(
                c ->
                    String.format(
                        "%s- %s %s a %s",
                        c.getNombre(), c.getDia(), c.getHorarioInicio(), c.getHorarioFin()))
            .collect(Collectors.toList());
    return ProfesorResponse.buildFromProfesor(profesor, clases);
  }

  public List<Profesor> findByNombreContaining(String nombre) {
    return profesorRepository.findByNombreApellidoContainingIgnoreCaseAndActivoTrue(nombre);
  }

  public List<Profesor> findByCursoNombreContaining(String curso) {
    Pageable paging = PageRequest.of(0, 100);
    return claseRepository
        .findByCursoNombreContainingIgnoreCaseAndActivoTrue(curso, paging)
        .getContent()
        .stream()
        .map(Clase::getProfesor)
        .collect(Collectors.toList());
  }
}
