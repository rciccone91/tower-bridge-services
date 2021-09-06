package com.houndsoft.towerbridge.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity(name = "profesores")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profesor extends AbstractEntity {

  @Column(nullable = false)
  @NotEmpty
  private String nombreApellido;

  @Column(nullable = false)
  private Integer dni;

  @Column(nullable = false)
  private Integer edad;

  @Column(length = 1000)
  private String detalles;

  @Column(nullable = false)
  @NotEmpty
  private String cbuCvu;

  @Column(nullable = false, length = 1000)
  @NotEmpty
  private String experienciaPrevia;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean valorHoraDiferenciado;

  @OneToOne
  @JoinColumn(name = "contacto_id")
  @Cascade(CascadeType.ALL)
  private Contacto contacto;

  @OneToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;
}
