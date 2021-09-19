package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.DesempenioAlumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DesempenioAlumnoRepository extends JpaRepository<DesempenioAlumno, Long>, JpaSpecificationExecutor<DesempenioAlumno> {

}
