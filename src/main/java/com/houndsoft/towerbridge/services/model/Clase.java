package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity(name = "clases")
public class Clase extends AbstractEntity implements Cloneable {

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
  private String link_videollamada;

  @Column(nullable = false)
  @NotEmpty
  private String clave_videollamada;

  @Column(nullable = false)
  @NotEmpty
  private String link_classroom;

  @Column(nullable = false)
  @NotEmpty
  private String clave_classroom;

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

  public Clase() {}

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

  public Dia getDia() {
    return dia;
  }

  public void setDia(Dia dia) {
    this.dia = dia;
  }

  public String getHorario() {
    return horario;
  }

  public void setHorario(String horario) {
    this.horario = horario;
  }

  public String getLink_videollamada() {
    return link_videollamada;
  }

  public void setLink_videollamada(String link_videollamada) {
    this.link_videollamada = link_videollamada;
  }

  public String getClave_videollamada() {
    return clave_videollamada;
  }

  public void setClave_videollamada(String clave_videollamada) {
    this.clave_videollamada = clave_videollamada;
  }

  public String getLink_classroom() {
    return link_classroom;
  }

  public void setLink_classroom(String link_classroom) {
    this.link_classroom = link_classroom;
  }

  public String getClave_classroom() {
    return clave_classroom;
  }

  public void setClave_classroom(String clave_classroom) {
    this.clave_classroom = clave_classroom;
  }

  public Profesor getProfesor() {
    return profesor;
  }

  public void setProfesor(Profesor profesor) {
    this.profesor = profesor;
  }

  public Curso getCurso() {
    return curso;
  }

  public void setCurso(Curso curso) {
    this.curso = curso;
  }
}
