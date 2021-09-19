package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CajaRepository extends JpaRepository<Caja, Long>, JpaSpecificationExecutor<Caja> {

}
