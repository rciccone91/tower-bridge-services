package com.houndsoft.towerbridge.services.repository;

import com.houndsoft.towerbridge.services.model.Contacto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ContactoRepository extends CrudRepository<Contacto, Long> {

}
