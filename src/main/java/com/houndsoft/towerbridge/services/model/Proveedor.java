package com.houndsoft.towerbridge.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity(name = "proveedores")
public class Proveedor extends AbstractEntity implements Cloneable {

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

    public Proveedor() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNroCuentaBancaria() {
        return nroCuentaBancaria;
    }

    public void setNroCuentaBancaria(String nroCuentaBancaria) {
        this.nroCuentaBancaria = nroCuentaBancaria;
    }
}
