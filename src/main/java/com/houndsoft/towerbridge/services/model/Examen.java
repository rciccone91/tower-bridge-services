package com.houndsoft.towerbridge.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity(name = "examenes")
public class Examen extends AbstractEntity implements Cloneable {

  @Column(nullable = false) @NotEmpty private String nombre;
  @Column(nullable = false) private Date fecha;

  @Column(nullable = false)
  @Max(100)
  @Min(0)
  private Integer nota;

  @Column(nullable = false,length = 1000)
  @NotEmpty
  private String correciones;

  @OneToOne
  @JoinColumn(name = "alumno_id")
  private Alumno alumno;

  @OneToOne
  @JoinColumn(name = "profesor_id")
  private Profesor profesor;
}
