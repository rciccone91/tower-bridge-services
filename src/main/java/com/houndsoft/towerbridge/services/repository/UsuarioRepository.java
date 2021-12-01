package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByUsernameAndPasswordAndActivoTrue(String username, String password);

    Optional<Usuario> findByUsername(String username);

    Boolean existsByUsername(String username);

    Page<Usuario> findByUsernameContainingIgnoreCaseAndActivoTrue(String username, Pageable paging);
}
