package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
public class DesempenioComentarios extends AbstractEntity implements Cloneable {

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

  public DesempenioComentarios() {}

  public String getComentariosObservaciones() {
    return comentariosObservaciones;
  }

  public void setComentariosObservaciones(String comentariosObservaciones) {
    this.comentariosObservaciones = comentariosObservaciones;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Profesor getProfesor() {
    return profesor;
  }

  public void setProfesor(Profesor profesor) {
    this.profesor = profesor;
  }

  public DesempenioAlumno getDesempenioAlumno() {
    return desempenioAlumno;
  }

  public void setDesempenioAlumno(DesempenioAlumno desempenioAlumno) {
    this.desempenioAlumno = desempenioAlumno;
  }
}
