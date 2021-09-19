package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LibroRepository extends JpaRepository<Libro, Long>, JpaSpecificationExecutor<LibroRepository> {

}

