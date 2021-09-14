package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Entity(name = "usuarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends AbstractEntity {

  @Column(nullable = false, unique = true)
  @NotEmpty
  private String username;

  @Column(nullable = false)
  @NotEmpty
  private String password; //TODO - hash

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Perfil perfil;

  @Column(nullable = false)
  private String email;

  public enum Perfil {
    ADMIN,
    PROFESOR,
    ALUMNO
  }
}