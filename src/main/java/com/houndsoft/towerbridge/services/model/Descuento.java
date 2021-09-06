package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity(name = "descuentos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Descuento extends AbstractEntity {

  @Column(nullable = false) @NotEmpty private String nombre;
  @Column(nullable = false) @NotEmpty private String descripcion;

  @Column(nullable = false)
  @Max(100)
  @Min(0)
  private Integer porcentaje;

  @Column(nullable = false) private Boolean activo;
}
