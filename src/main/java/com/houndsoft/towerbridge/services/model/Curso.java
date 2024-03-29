package com.houndsoft.towerbridge.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity(name = "cursos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curso extends AbstractEntity {

  @Column(nullable = false)
  @NotEmpty
  private String nombre;

  private String descripcion;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoDeCurso tipoDeCurso;

  @ManyToMany
  @JoinTable(
      name = "libros_x_curso",
      joinColumns = @JoinColumn(name = "curso_id"),
      inverseJoinColumns = @JoinColumn(name = "libro_id"))
  List<Libro> librosDelCurso;

  @Column(nullable = false)
  private Integer valorArancel;

  private Integer valorExamen;

  @Column(nullable = false)
  private Integer valorHoraProfesor;

  public enum TipoDeCurso {
    CAMBRIDGE_INTERNATIONAL,
    ADULTOS,
    ESPECIFICOS;
  }
}
