package com.houndsoft.towerbridge.services.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Date fechaCreacion;
  @Column(nullable = false)
  private Date fechaActualizacion;
  @Column(nullable = false)
  private Boolean activo;

  public AbstractEntity() {
    if(fechaCreacion == null){
      this.fechaCreacion = Date.from(Instant.now());
    }
    this.fechaActualizacion = Date.from(Instant.now());
    this.activo = true;
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
