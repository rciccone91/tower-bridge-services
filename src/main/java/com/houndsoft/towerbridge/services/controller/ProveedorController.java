package com.houndsoft.towerbridge.services.controller;

import com.houndsoft.towerbridge.services.model.Profesor;
import com.houndsoft.towerbridge.services.model.Proveedor;
import com.houndsoft.towerbridge.services.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/proveedores")
    public ResponseEntity<List<Proveedor>> getAllProveedores() {
        final List<Proveedor> allActive = proveedorService.getAllActive();
        return ResponseEntity.ok(allActive);
    }
}
