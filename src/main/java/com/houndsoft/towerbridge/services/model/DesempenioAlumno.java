package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "desemp_alumno")
public class DesempenioAlumno extends AbstractEntity implements Cloneable {
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

  public DesempenioAlumno() {}

  public Desempenio getLectura() {
    return lectura;
  }

  public void setLectura(Desempenio lectura) {
    this.lectura = lectura;
  }

  public Desempenio getEscritura() {
    return escritura;
  }

  public void setEscritura(Desempenio escritura) {
    this.escritura = escritura;
  }

  public Desempenio getEscucha() {
    return escucha;
  }

  public void setEscucha(Desempenio escucha) {
    this.escucha = escucha;
  }

  public Desempenio getFonetica() {
    return fonetica;
  }

  public void setFonetica(Desempenio fonetica) {
    this.fonetica = fonetica;
  }

  public Desempenio getGramatica() {
    return gramatica;
  }

  public void setGramatica(Desempenio gramatica) {
    this.gramatica = gramatica;
  }

  public Desempenio getVocabulario() {
    return vocabulario;
  }

  public void setVocabulario(Desempenio vocabulario) {
    this.vocabulario = vocabulario;
  }

  public Alumno getAlumno() {
    return alumno;
  }

  public void setAlumno(Alumno alumno) {
    this.alumno = alumno;
  }

  public List<DesempenioComentarios> getComentarios() {
    return comentarios;
  }

  public void setComentarios(List<DesempenioComentarios> comentarios) {
    this.comentarios = comentarios;
  }

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
