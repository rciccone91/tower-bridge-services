package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity(name = "libros")
public class Libro extends AbstractEntity implements Cloneable {

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

  public Libro() {}

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDetalle() {
    return detalle;
  }

  public void setDetalle(String detalle) {
    this.detalle = detalle;
  }

  public TipoDeLibro getTipoDeLibro() {
    return tipoDeLibro;
  }

  public void setTipoDeLibro(TipoDeLibro tipoDeLibro) {
    this.tipoDeLibro = tipoDeLibro;
  }

//  public String getTipo() {
//    return tipo;
//  }
//
//  public void setTipo(String tipo) {
//    this.tipo = tipo;
//  }

  public String getLink_descarga() {
    return link_descarga;
  }

  public void setLink_descarga(String link_descarga) {
    this.link_descarga = link_descarga;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Integer getStock_fotocopia() {
    return stock_fotocopia;
  }

  public void setStock_fotocopia(Integer stock_fotocopia) {
    this.stock_fotocopia = stock_fotocopia;
  }

  public List<Curso> getCursos() {
    return cursos;
  }

  public void setCursos(List<Curso> cursos) {
    this.cursos = cursos;
  }
}
