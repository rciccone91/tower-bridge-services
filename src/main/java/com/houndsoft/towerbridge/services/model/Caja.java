package com.houndsoft.towerbridge.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Caja extends AbstractEntity implements Cloneable {

  @Column(nullable = false) private Integer valorActual;

  @OneToOne
  @JoinColumn(name = "ultimo_movimiento_id")
  private Movimiento ultimoMovimiento;

  public Integer getValorActual() {
    return valorActual;
  }

  public void setValorActual(Integer valorActual) {
    this.valorActual = valorActual;
  }

  public Movimiento getUltimoMovimiento() {
    return ultimoMovimiento;
  }

  public void setUltimoMovimiento(Movimiento ultimoMovimiento) {
    this.ultimoMovimiento = ultimoMovimiento;
  }
}
