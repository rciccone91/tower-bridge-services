package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesempenioComentarios extends AbstractEntity {

  @Column(nullable = false,length = 1500)
  @NotEmpty
  private String comentariosObservaciones;

  @Column(nullable = false) private Date fecha;

  @OneToOne
  @JoinColumn(name = "profesor_id")
  private Profesor profesor;

  @ManyToOne
  @JoinColumn(name = "fk_desempenio_alumno", nullable = false, updatable = false)
  private DesempenioAlumno desempenioAlumno;
}
