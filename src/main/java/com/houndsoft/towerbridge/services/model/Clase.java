package com.houndsoft.towerbridge.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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
  private Integer horarioInicio;

  @Column(nullable = false)
  private Integer horarioFin;

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

  @ManyToMany
  @JoinTable(
          name = "alumnos_x_clase",
          joinColumns = @JoinColumn(name = "clase_id"),
          inverseJoinColumns = @JoinColumn(name = "alumno_id"))
  @JsonIgnoreProperties("clases")
  @Cascade(CascadeType.ALL)
  private List<Alumno> alumnosAnotados;

  public enum Dia {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO
  }
}
