package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity(name = "clases")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clase extends AbstractEntity {

  @Column(nullable = false)
  @NotEmpty
  private String nombre;

  @Column(length = 1000)
  private String descripcion;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Dia dia;

  @Column(nullable = false)
  @NotEmpty
  private String horario;

  @Column(nullable = false)
  @NotEmpty
  private String linkVideollamada;

  @Column(nullable = false)
  @NotEmpty
  private String claveVideollamada;

  @Column(nullable = false)
  @NotEmpty
  private String linkClassroom;

  @Column(nullable = false)
  @NotEmpty
  private String claveClassroom;

  @OneToOne
  @JoinColumn(name = "profesor_id")
  private Profesor profesor;

  @OneToOne
  @JoinColumn(name = "curso_id")
  private Curso curso;

  public enum Dia {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO
  }
}
