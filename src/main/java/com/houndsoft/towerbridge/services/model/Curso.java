package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity(name = "cursos")
public class Curso extends AbstractEntity implements Cloneable {

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
  private String valorHoraProfesor;

  public enum TipoDeCurso {
    CAMBRIDGE_INTERNATIONAL,
    ADULTOS,
    ESPECIFICOS;
  }

  public Curso() {}

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public TipoDeCurso getTipoDeCurso() {
    return tipoDeCurso;
  }

  public void setTipoDeCurso(TipoDeCurso tipoDeCurso) {
    this.tipoDeCurso = tipoDeCurso;
  }

  public List<Libro> getLibrosDelCurso() {
    return librosDelCurso;
  }

  public void setLibrosDelCurso(List<Libro> librosDelCurso) {
    this.librosDelCurso = librosDelCurso;
  }

  public Integer getValorArancel() {
    return valorArancel;
  }

  public void setValorArancel(Integer valorArancel) {
    this.valorArancel = valorArancel;
  }

  public Integer getValorExamen() {
    return valorExamen;
  }

  public void setValorExamen(Integer valorExamen) {
    this.valorExamen = valorExamen;
  }

  public String getValorHoraProfesor() {
    return valorHoraProfesor;
  }

  public void setValorHoraProfesor(String valorHoraProfesor) {
    this.valorHoraProfesor = valorHoraProfesor;
  }
}
