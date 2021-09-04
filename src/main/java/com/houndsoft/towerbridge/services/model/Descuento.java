package com.houndsoft.towerbridge.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity(name = "descuentos")
public class Descuento extends AbstractEntity implements Cloneable {

  @Column(nullable = false) @NotEmpty private String nombre;
  @Column(nullable = false) @NotEmpty private String descripcion;

  @Column(nullable = false)
  @Max(100)
  @Min(0)
  private Integer porcentaje;

  @Column(nullable = false) private Boolean activo;

  public Descuento() {}

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

  public Integer getPorcentaje() {
    return porcentaje;
  }

  public void setPorcentaje(Integer porcentaje) {
    this.porcentaje = porcentaje;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }
}
