package com.houndsoft.towerbridge.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity(name = "contactos")
public class Contacto extends AbstractEntity implements Cloneable {

  @Column(nullable = false)
  @NotEmpty
  private String domicilio;

  @Column(nullable = false)
  @NotEmpty
  private String telefono;

  @Column(nullable = false)
  @NotEmpty
  @Email
  private String email;

  public Contacto() {}

  public Contacto(
      @NotEmpty String domicilio, @NotEmpty String telefono, @NotEmpty @Email String email) {
    super();
    this.domicilio = domicilio;
    this.telefono = telefono;
    this.email = email;
  }

  public String getDomicilio() {
    return domicilio;
  }

  public void setDomicilio(String domicilio) {
    this.domicilio = domicilio;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Contacto)) return false;
//    if (!super.equals(o)) return false;
    Contacto contacto = (Contacto) o;
    if (getId().equals(contacto.getId())) return true;
    return domicilio.equals(contacto.domicilio) &&
            telefono.equals(contacto.telefono) &&
            email.equals(contacto.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), domicilio, telefono, email);
  }

  @Override
  public String toString() {
    return "Contacto{"
        + "domicilio='"
        + domicilio
        + '\''
        + ", telefono='"
        + telefono
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }
}
