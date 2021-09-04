package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.houndsoft.towerbridge.services.utils.CollectionUtils.iterableToList;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = iterableToList(usuarioRepository.findAll());
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") long id) {
        return null;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        return null;
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario) {
        return null;
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") long id) {
        return null;
    }

}
