package com.houndsoft.towerbridge.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity(name = "profesores")
public class Profesor extends AbstractEntity implements Cloneable {

  @Column(nullable = false)
  @NotEmpty
  private String nombreApellido;

  @Column(nullable = false)
  private Integer dni;

  @Column(nullable = false)
  private Integer edad;

  @Column(length = 1000)
  private String detalles;

  @Column(nullable = false)
  @NotEmpty
  private String cbuCvu;

  @Column(nullable = false, length = 1000)
  @NotEmpty
  private String experienciaPrevia;

  @Column(nullable = false)
  private Boolean valorHoraDiferenciado;

  @OneToOne
  @JoinColumn(name = "contacto_id")
  @Cascade(CascadeType.ALL)
  private Contacto contacto;

  @OneToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  public Profesor() {
    this.valorHoraDiferenciado = false;
  }

  public String getNombreApellido() {
    return nombreApellido;
  }

  public void setNombreApellido(String nombreApellido) {
    this.nombreApellido = nombreApellido;
  }

  public Integer getDni() {
    return dni;
  }

  public void setDni(Integer dni) {
    this.dni = dni;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  public String getDetalles() {
    return detalles;
  }

  public void setDetalles(String detalles) {
    this.detalles = detalles;
  }

  public String getCbuCvu() {
    return cbuCvu;
  }

  public void setCbuCvu(String cbuCvu) {
    this.cbuCvu = cbuCvu;
  }

  public String getExperienciaPrevia() {
    return experienciaPrevia;
  }

  public void setExperienciaPrevia(String experienciaPrevia) {
    this.experienciaPrevia = experienciaPrevia;
  }

  public Boolean getValorHoraDiferenciado() {
    return valorHoraDiferenciado;
  }

  public void setValorHoraDiferenciado(Boolean valorHoraDiferenciado) {
    this.valorHoraDiferenciado = valorHoraDiferenciado;
  }

  public Contacto getContacto() {
    return contacto;
  }

  public void setContacto(Contacto contacto) {
    this.contacto = contacto;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}
