package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.DesempenioComentarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DesempenioComentariosRepository extends JpaRepository<DesempenioComentarios,Long>, JpaSpecificationExecutor<DesempenioComentarios> {
}
