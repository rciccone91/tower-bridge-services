package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Padre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PadreRepository extends JpaRepository<Padre, Long> {

}
