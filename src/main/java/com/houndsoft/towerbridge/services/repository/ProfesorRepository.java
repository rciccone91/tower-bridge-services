package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long>, JpaSpecificationExecutor<Profesor> {

    List<Profesor> findByNombreApellidoContainingIgnoreCaseAndActivoTrue(String nombre);

}

