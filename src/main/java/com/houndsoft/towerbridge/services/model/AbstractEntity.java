package com.houndsoft.towerbridge.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(nullable = false)
  private Date fechaCreacion;
  @Column(nullable = false)
  private Date fechaActualizacion;
  @Column(nullable = false, columnDefinition = "boolean default true")
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
}
