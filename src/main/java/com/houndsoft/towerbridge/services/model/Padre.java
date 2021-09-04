package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "padres")
public class Padre extends AbstractEntity implements Cloneable {

  @Column(nullable = false)
  @NotEmpty
  private String nombreApellido;

  @Column(nullable = false)
  private Integer DNI;

  public Padre() {}

  public Padre(
      @NotNull @NotEmpty String nombreApellido,
      @NotNull Integer DNI,
      Contacto contacto,
      String detalles) {
    super();
    this.nombreApellido = nombreApellido;
    this.DNI = DNI;
    this.contacto = contacto;
    this.detalles = detalles;
  }

  @OneToOne
  @JoinColumn(name = "contacto_id")
  private Contacto contacto;

  @ManyToMany(mappedBy = "padresACargo")
  List<Alumno> alumnos;

  private String detalles;

  public String getNombreApellido() {
    return nombreApellido;
  }

  public void setNombreApellido(String nombreApellido) {
    this.nombreApellido = nombreApellido;
  }

  public Integer getDNI() {
    return DNI;
  }

  public void setDNI(Integer DNI) {
    this.DNI = DNI;
  }

  public Contacto getContacto() {
    return contacto;
  }

  public void setContacto(Contacto contacto) {
    this.contacto = contacto;
  }

  public String getDetalles() {
    return detalles;
  }

  public void setDetalles(String detalles) {
    this.detalles = detalles;
  }

  @Override
  public Padre clone() {
    try {
      return (Padre) super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  @Override
  public String toString() {
    return "Padre{"
        + "nombreApellido='"
        + nombreApellido
        + '\''
        + ", DNI="
        + DNI
        + ", contacto="
        + contacto
        + ", detalles='"
        + detalles
        + '\''
        + '}';
  }
}
