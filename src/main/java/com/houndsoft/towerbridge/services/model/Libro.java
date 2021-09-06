package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity(name = "libros")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Libro extends AbstractEntity {

  @Column(nullable = false)
  @NotEmpty
  private String nombre;

  @Column(nullable = false)
  @NotEmpty
  private String detalle;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoDeLibro tipoDeLibro;

//  @Column(nullable = false)
//  @NotEmpty
//  private String tipo;

  private String link_descarga;

  @Column(nullable = false)
  private Integer stock;

  @Column(nullable = false)
  private Integer stock_fotocopia;

  @ManyToMany(mappedBy = "librosDelCurso")
  List<Curso> cursos;

  public enum TipoDeLibro {
    STUDENTS_WORKBOOK,
    WRITING_BOOK,
    READERS;
  }
}
