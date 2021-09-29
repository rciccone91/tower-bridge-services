package com.houndsoft.towerbridge.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name = "padres")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Padre extends AbstractEntity {

  @Column(nullable = false)
  @NotEmpty
  private String nombreApellido;

  @Column(nullable = false)
  private Integer DNI;

  @OneToOne
  @JoinColumn(name = "contacto_id")
  @Cascade(CascadeType.ALL)
  private Contacto contacto;

  @ManyToMany(mappedBy = "padresACargo")
  @JsonIgnoreProperties("padresACargo")
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
