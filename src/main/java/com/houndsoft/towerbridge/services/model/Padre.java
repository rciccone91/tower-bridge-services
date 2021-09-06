package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "padres")
public class Padre extends AbstractEntity {

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
