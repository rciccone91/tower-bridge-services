package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.request.UsuarioDTO;
import com.houndsoft.towerbridge.services.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<Map<String, Object>> getPaginatedUsuarios(@RequestParam(required = false) String username,
                                                                   @RequestParam(defaultValue = "0") int page) {
        try {
            List<Usuario> usuarios;
            Pageable paging = PageRequest.of(page, 8, Sort.by(Sort.Direction.ASC, "id"));

            Page<Usuario> pageUsuario;
            if (username != null) {
                pageUsuario = usuarioService.findByUsernameContaining(username, paging);
            } else {
                pageUsuario = usuarioService.getPaginatedUsuario(paging);
            }

            usuarios = pageUsuario.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("usuarios", usuarios);
            response.put("currentPage", pageUsuario.getNumber());
            response.put("totalItems", pageUsuario.getTotalElements());
            response.put("totalPages", pageUsuario.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") long id) {
        final Usuario usuario = usuarioService.getUsuarioDetail(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuarios/a-asignar")
    public ResponseEntity<List<Map<String, Object>>> getUsuarioByPerfil(@RequestParam("perfil") String perfil) {
        final List<Map<String, Object>> usuarios = usuarioService.getUsuariosByPerfil(perfil);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioDTO usuarioDto) {
        Usuario usuario = usuarioService.createUsuario(usuarioDto);
        return ResponseEntity.status(201).body(usuario);
    }

    @PatchMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") long id, @RequestBody UsuarioDTO usuarioDto) {
        Usuario usuario = usuarioService.updateUsuario(id, usuarioDto);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") long id) {
        usuarioService.softDeleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
