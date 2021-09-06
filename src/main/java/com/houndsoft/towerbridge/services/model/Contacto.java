package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity(name = "contactos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contacto extends AbstractEntity {

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Contacto)) return false;
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
