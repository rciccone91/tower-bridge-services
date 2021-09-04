package com.houndsoft.towerbridge.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Entity(name = "usuarios")
public class Usuario extends AbstractEntity implements Cloneable {

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
  private Boolean activo;

  @Column(nullable = false)
  private String email;

  public enum Perfil {
    ADMIN,
    PROFESOR,
    ALUMNO
  }

  public Usuario() {}

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Perfil getPerfil() {
    return perfil;
  }

  public void setPerfil(Perfil perfil) {
    this.perfil = perfil;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
