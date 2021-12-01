package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.dao.UsuarioDao;
import com.houndsoft.towerbridge.services.exception.LoginInvalidoException;
import com.houndsoft.towerbridge.services.exception.UsuarioSinAlumoAsociadoException;
import com.houndsoft.towerbridge.services.exception.UsuarioSinProfesorAsociadoException;
import com.houndsoft.towerbridge.services.exception.UsuarioYaExistenteException;
import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Profesor;
import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.repository.AlumnoRepository;
import com.houndsoft.towerbridge.services.repository.ProfesorRepository;
import com.houndsoft.towerbridge.services.repository.UsuarioRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.request.LoginRequest;
import com.houndsoft.towerbridge.services.request.UsuarioDTO;
import com.houndsoft.towerbridge.services.response.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class UsuarioService implements CommonFilter {

  @Autowired UsuarioRepository usuarioRepository;
  @Autowired ProfesorRepository profesorRepository;
  @Autowired AlumnoRepository alumnoRepository;

  @Autowired UsuarioDao usuarioDao;

  public Page<Usuario> findByUsernameContaining(String username, Pageable paging) {
    return usuarioRepository.findByUsernameContainingIgnoreCaseAndActivoTrue(username, paging);
  }

  public Page<Usuario> getPaginatedUsuario(Pageable paging) {
    return usuarioRepository.findAll(isActive(), paging);
  }

  public Usuario getUsuarioDetail(long id) {
    final Usuario usuario = usuarioRepository.getById(id);
    return usuario;
  }

  public Usuario createUsuario(UsuarioDTO usuarioDto) {
    if(usuarioRepository.existsByUsername(usuarioDto.getUsername())){
      throw new UsuarioYaExistenteException(usuarioDto.getUsername());
    }
    Usuario usuario = usuarioDto.buildUsuario();
    usuarioRepository.save(usuario);
    return usuario;
  }

  public Usuario updateUsuario(Long id, UsuarioDTO usuarioDto) {
    final Usuario retrievedUsuario = usuarioRepository.getById(id);
    if (retrievedUsuario.isPersisted()) {
      final Optional<Usuario> maybeUsuario = usuarioRepository.findByUsername(usuarioDto.getUsername());
      if(maybeUsuario.isPresent() && !maybeUsuario.get().getId().equals(id)){
        throw new UsuarioYaExistenteException(usuarioDto.getUsername());
      }
      Usuario usuario = usuarioDto.buildUsuario();
      usuario.setId(id);
      usuarioRepository.save(usuario);
      return usuario;
    } else throw new RuntimeException("El usuario no existe.");
  }

  public void softDeleteUsuario(long id) {
    final Usuario retrievedUsuario = usuarioRepository.getById(id);
    if (retrievedUsuario.isPersisted()) {
      retrievedUsuario.setActivo(false);
      usuarioRepository.save(retrievedUsuario);
    } else throw new RuntimeException("El usuario no existe.");
  }

  public List<Map<String, Object>> getUsuariosByPerfil(String perfil) {
    return usuarioDao.getUnassinedUsuariosByPerfil(Usuario.Perfil.valueOf(perfil));
  }

  public UsuarioResponse login(LoginRequest loginRequest) {
    return usuarioRepository
        .findByUsernameAndPasswordAndActivoTrue(
            loginRequest.getUsername(), loginRequest.getPassword()).map(u -> {
              Long relatedId = null;
              if (u.getPerfil().equals(Usuario.Perfil.ALUMNO)) {
                final Alumno alumno = alumnoRepository.findAlumnoByUsuarioEqualsAndActivoTrue(u);
                if(alumno == null){
                  throw new UsuarioSinAlumoAsociadoException(u.getUsername());
                }
                relatedId = alumno.getId();
              }
              if (u.getPerfil().equals(Usuario.Perfil.PROFESOR)) {
                final Profesor profesor = profesorRepository.findProfesorByUsuarioEqualsAndActivoTrue(u);
                if(profesor == null){
                  throw new UsuarioSinProfesorAsociadoException(u.getUsername());
                }
                relatedId = profesor.getId();
              }
              return UsuarioResponse.buildFromUsuario(u,relatedId);
            })
        .orElseThrow(LoginInvalidoException::new);
  }
}
