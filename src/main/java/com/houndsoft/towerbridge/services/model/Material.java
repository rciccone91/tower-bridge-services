package com.houndsoft.towerbridge.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "materiales")
public class Material extends AbstractEntity implements Cloneable {

    @Column(nullable = false)
    private String nombre;
    @Column(length = 500)
    private String detalle;
    @Column(nullable = false)
    private Integer stock;

    public Material() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
