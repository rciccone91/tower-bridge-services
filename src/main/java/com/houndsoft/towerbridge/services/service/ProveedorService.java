package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.model.Proveedor;
import com.houndsoft.towerbridge.services.repository.ProveedorRepository;
import com.houndsoft.towerbridge.services.repository.filter.CommonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.houndsoft.towerbridge.services.repository.filter.CommonFilter.isActive;

@Service
public class ProveedorService implements CommonFilter {

  @Autowired ProveedorRepository proveedorRepository;

  public List<Proveedor> getAllActive() {
    return proveedorRepository.findAll(isActive());
  }

}
