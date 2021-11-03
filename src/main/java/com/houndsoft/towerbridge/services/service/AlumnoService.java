package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.exception.AlumnoInscriptoEnClaseException;
import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Padre;
import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.repository.AlumnoRepository;
import com.houndsoft.towerbridge.services.repository.ContactoRepository;
import com.houndsoft.towerbridge.services.repository.PadreRepository;
import com.houndsoft.towerbridge.services.repository.UsuarioRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.request.AlumnoDTO;
import com.houndsoft.towerbridge.services.response.AlumnoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class AlumnoService implements CommonFilter {

  @Autowired AlumnoRepository alumnoRepository;
  @Autowired ContactoRepository contactoRepository;
  @Autowired UsuarioRepository usuarioRepository;
  @Autowired PadreRepository padreRepository;

  public List<Alumno> getAllActive() {
    return alumnoRepository.findAll(isActive());
  }

  public AlumnoResponse getAlumnoDetail(Long id) {
    final Alumno alumno = alumnoRepository.getById(id);
    return AlumnoResponse.buildFromAlumno(alumno);
  }

  public Alumno createAlumno(AlumnoDTO alumnoDto) {
    Alumno alumno = alumnoDto.buildAlumno();
    final Optional<Usuario> usuario = getUsuario(alumnoDto);
    final List<Padre> padresACargo = getPadresACargo(alumnoDto);
    alumnoRepository.save(alumno);
    usuario.ifPresent(alumno::setUsuario);
    alumno.setPadresACargo(padresACargo);
    alumnoRepository.save(alumno);
    return alumno;
  }

  private List<Padre> getPadresACargo(AlumnoDTO alumnoDto) {
    List<Padre> padres = new ArrayList<>();
    alumnoDto.getPadresACargo().forEach(p -> {
      final Padre padre = padreRepository.getById(p);
      padres.add(padre);
    });
    return padres;
  }

  private Optional<Usuario> getUsuario(AlumnoDTO alumnoDto) {
    if (alumnoDto.getUsuarioId() != null) {
      return usuarioRepository.findById(alumnoDto.getUsuarioId());
    } else return Optional.empty();
  }

  public Alumno upadeAlumno(Long id, AlumnoDTO alumnoDTO) {
    final Alumno retrievedAlumno = alumnoRepository.getById(id);
    if (retrievedAlumno.isPersisted()) {
      final Optional<Usuario> usuario = getUsuario(alumnoDTO);
      final List<Padre> padresACargo = getPadresACargo(alumnoDTO);
      Long contactoId = retrievedAlumno.getContacto().getId();
      Alumno alumno = alumnoDTO.buildAlumno();
      alumno.setId(id);
      alumno.getContacto().setId(contactoId);
      usuario.ifPresent(alumno::setUsuario);
      alumno.setPadresACargo(padresACargo);
      alumnoRepository.save(alumno);
      return alumno;
    } else throw new RuntimeException("El alumno no existe.");
  }

  public void softDeleteAlumno(long id) {
    final Alumno retrievedAlumno = alumnoRepository.getById(id);
    if (retrievedAlumno.isPersisted()) {
      if (!retrievedAlumno.getClases().isEmpty()) {
        throw new AlumnoInscriptoEnClaseException(
            retrievedAlumno.getNombreApellido(), retrievedAlumno.getClases().get(0).getNombre());
      }
      retrievedAlumno.setActivo(false);
      alumnoRepository.save(retrievedAlumno);
    } else throw new RuntimeException("El alumno no existe.");
  }

  public Page<Alumno> findByNombreApellidoContaining(String nombreApellido, Pageable paging) {
    return alumnoRepository.findByNombreApellidoContainingIgnoreCaseAndActivoTrue(
        nombreApellido, paging);
  }

  public Page<Alumno> findByAnioEscolarContaining(String anioEscolar, Pageable paging) {
    return alumnoRepository.findByAnioEscolarContainingIgnoreCaseAndActivoTrue(anioEscolar, paging);
  }

  public Page<Alumno> getPaginatedAlumno(Pageable paging) {
    return alumnoRepository.findAll(isActive(), paging);
  }
}
