package com.houndsoft.towerbridge.services.service;


import com.houndsoft.towerbridge.services.dao.UsuarioDao;
import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.repository.UsuarioRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import com.houndsoft.towerbridge.services.request.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class UsuarioService implements CommonFilter {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioDao usuarioDao;


    public Page<Usuario> findByUsernameContaining(String username, Pageable paging) {
        return usuarioRepository.findByUsernameContainingIgnoreCaseAndActivoTrue(username,paging);
    }

    public Page<Usuario> getPaginatedUsuario(Pageable paging) {
        return usuarioRepository.findAll(isActive(),paging);
    }

    public Usuario getUsuarioDetail(long id) {
        final Usuario usuario = usuarioRepository.getById(id);
        return usuario;
    }

    public Usuario createUsuario(UsuarioDTO usuarioDto) {
        Usuario usuario = usuarioDto.buildUsuario();
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario updateUsuario(long id, UsuarioDTO usuarioDto) {
        final Usuario retrievedUsuario = usuarioRepository.getById(id);
        if (retrievedUsuario.isPersisted()) {
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
}
