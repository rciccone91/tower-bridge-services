package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity(name = "proveedores")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor extends AbstractEntity {

    @Column(nullable = false)
    @NotEmpty
    private String nombre;
    @Column(nullable = false)
    @NotEmpty
    private String descripcion;
    @Column(nullable = false)
    @NotEmpty
    private String direccion;
    @Column(nullable = false)
    @NotEmpty
    private String telefono;
    @Column(nullable = false)
    @NotEmpty
    @Email
    private String email;
    @Column(nullable = false)
    @NotEmpty
    private String cuit;
    @Column(nullable = false)
    @NotEmpty
    private String nroCuentaBancaria;
}
