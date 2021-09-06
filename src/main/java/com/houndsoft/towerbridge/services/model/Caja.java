package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Caja extends AbstractEntity {

  @Column(nullable = false) private Integer valorActual;

  @OneToOne
  @JoinColumn(name = "ultimo_movimiento_id")
  private Movimiento ultimoMovimiento;
}
