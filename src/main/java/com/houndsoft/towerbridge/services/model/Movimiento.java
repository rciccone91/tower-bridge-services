package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity(name = "movimientos")
public class Movimiento extends AbstractEntity implements Cloneable {

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoDeMovimiento tipoMovimiento;

  @Column(nullable = false)
  private Integer monto;

  @Column(nullable = false)
  @NotEmpty
  private String detalle;

  @Column(nullable = false)
  private Date fecha;

  private String mesAbonado;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MedioDePago medioDePago;

  @OneToOne
  @JoinColumn(name = "alumno_id")
  private Alumno alumno;

  @OneToOne
  @JoinColumn(name = "proveedor_id")
  private Proveedor proveedor;

  @OneToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  public enum TipoDeMovimiento {
    COBRO,
    PAGO
  }

  public enum MedioDePago {
    EFECTIVO,
    TRANSFERENCIA_BANCARIA,
    MERCADO_PAGO
  }

  public Movimiento() {}

  public TipoDeMovimiento getTipoMovimiento() {
    return tipoMovimiento;
  }

  public void setTipoMovimiento(TipoDeMovimiento tipoMovimiento) {
    this.tipoMovimiento = tipoMovimiento;
  }

  public Integer getMonto() {
    return monto;
  }

  public void setMonto(Integer monto) {
    this.monto = monto;
  }

  public String getDetalle() {
    return detalle;
  }

  public void setDetalle(String detalle) {
    this.detalle = detalle;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getMesAbonado() {
    return mesAbonado;
  }

  public void setMesAbonado(String mesAbonado) {
    this.mesAbonado = mesAbonado;
  }

  public MedioDePago getMedioDePago() {
    return medioDePago;
  }

  public void setMedioDePago(MedioDePago medioDePago) {
    this.medioDePago = medioDePago;
  }

  public Alumno getAlumno() {
    return alumno;
  }

  public void setAlumno(Alumno alumno) {
    this.alumno = alumno;
  }

  public Proveedor getProveedor() {
    return proveedor;
  }

  public void setProveedor(Proveedor proveedor) {
    this.proveedor = proveedor;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}
