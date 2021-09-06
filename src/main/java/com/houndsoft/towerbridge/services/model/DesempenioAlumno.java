package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "desemp_alumno")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesempenioAlumno extends AbstractEntity {
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Desempenio lectura;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Desempenio escritura;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Desempenio escucha;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Desempenio fonetica;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Desempenio gramatica;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Desempenio vocabulario;

  @OneToOne
  @JoinColumn(name = "alumno_id")
  private Alumno alumno;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "desempenioAlumno")
  private List<DesempenioComentarios> comentarios;

  public enum Desempenio {
    REGULAR("R"),
    BIEN("B"),
    BIEN_MAS("B+"),
    MUY_BIEN("MB"),
    MUY_BIEN_MAS("MB+"),
    EXCELENTE("E");

    private String nota;

    Desempenio(String nota) {
      this.nota = nota;
    }
  }
}
