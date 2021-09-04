package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity(name = "alumnos")
public class Alumno extends AbstractEntity implements Cloneable {

  @Column(nullable = false)
  @NotEmpty
  private String nombreApellido;

  @Column(nullable = false)
  private Integer edad;

  @Column(nullable = false)
  private Integer dni;

  private String anioEscolar;
  private String colegio;

  @Column(nullable = false, length = 1000)
  private String nivelIngles;

  @NotEmpty
  @Column(nullable = false, length = 1000)
  private String institucionesPrevias;

  @Column(length = 1000)
  private String detalles;

  private Boolean rindeExamen;

  @OneToOne
  @JoinColumn(name = "contacto_id")
  private Contacto contacto;

  @OneToOne
  @JoinColumn(name = "descuento_id")
  private Descuento descuento;

  @ManyToMany
  @JoinTable(
      name = "padres_x_alumno",
      joinColumns = @JoinColumn(name = "alumno_id"),
      inverseJoinColumns = @JoinColumn(name = "padre_id"))
  List<Padre> padresACargo;

  public Alumno() {}

  public String getNombreApellido() {
    return nombreApellido;
  }

  public void setNombreApellido(String nombreApellido) {
    this.nombreApellido = nombreApellido;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  public Integer getDni() {
    return dni;
  }

  public void setDni(Integer dni) {
    this.dni = dni;
  }

  public String getAnioEscolar() {
    return anioEscolar;
  }

  public void setAnioEscolar(String anioEscolar) {
    this.anioEscolar = anioEscolar;
  }

  public String getColegio() {
    return colegio;
  }

  public void setColegio(String colegio) {
    this.colegio = colegio;
  }

  public String getNivelIngles() {
    return nivelIngles;
  }

  public void setNivelIngles(String nivelIngles) {
    this.nivelIngles = nivelIngles;
  }

  public String getInstitucionesPrevias() {
    return institucionesPrevias;
  }

  public void setInstitucionesPrevias(String institucionesPrevias) {
    this.institucionesPrevias = institucionesPrevias;
  }

  public String getDetalles() {
    return detalles;
  }

  public void setDetalles(String detalles) {
    this.detalles = detalles;
  }

  public Boolean getRindeExamen() {
    return rindeExamen;
  }

  public void setRindeExamen(Boolean rindeExamen) {
    this.rindeExamen = rindeExamen;
  }

  public Contacto getContacto() {
    return contacto;
  }

  public void setContacto(Contacto contacto) {
    this.contacto = contacto;
  }

  public List<Padre> getPadresACargo() {
    return padresACargo;
  }

  public void setPadresACargo(List<Padre> padresACargo) {
    this.padresACargo = padresACargo;
  }

  public Descuento getDescuento() {
    return descuento;
  }

  public void setDescuento(Descuento descuento) {
    this.descuento = descuento;
  }
}
