package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DescuentoRepository extends JpaRepository<Descuento, Long>, JpaSpecificationExecutor<Descuento> {

}
