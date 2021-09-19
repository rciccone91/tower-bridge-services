package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExamenRepository extends JpaRepository<Examen, Long>, JpaSpecificationExecutor<Examen> {

}
