package com.houndsoft.towerbridge.services.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Date fechaCreacion;
  @Column(nullable = false)
  private Date fechaActualizacion;

  public AbstractEntity() {
    if(fechaCreacion == null){
      this.fechaCreacion = Date.from(Instant.now());
    }
    this.fechaActualizacion = Date.from(Instant.now());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isPersisted() {
    return id != null;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return getId().hashCode();
    }
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractEntity other = (AbstractEntity) obj;
    if (getId() == null || other.getId() == null) {
      return false;
    }
    return getId().equals(other.getId());
  }
}
