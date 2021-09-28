package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Padre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface PadreRepository extends JpaRepository<Padre, Long>, JpaSpecificationExecutor<Padre> {

    Page<Padre> findByNombreApellidoContainingIgnoreCaseAndActivoTrue(String nombreApellido, Pageable pageable);

    Page<Padre> findByAlumnosNombreApellidoContainingIgnoreCase(String nombreApellido, Pageable pageable);
}
