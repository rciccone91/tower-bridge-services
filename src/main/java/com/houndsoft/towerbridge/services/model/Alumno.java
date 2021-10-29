package com.houndsoft.towerbridge.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.houndsoft.towerbridge.services.converters.YearMonthDateAttributeConverter;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.YearMonth;
import java.util.List;

@Entity(name = "alumnos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alumno extends AbstractEntity {

  @Column(nullable = false)
  @NotEmpty
  private String nombreApellido;

  @Column(nullable = false)
  private String fechaDeNacimiento;

  @Column(nullable = false)
  private Integer dni;

  private String anioEscolar;

  private String colegio;

  @Column(nullable = false, length = 1000)
  private String nivelIngles;

  @NotEmpty
  @Column(nullable = false, length = 1000)
  private String institucionesPrevias;

  @Column(length = 1000)
  private String detalles;

  private Boolean rindeExamen;

  @Convert(converter = YearMonthDateAttributeConverter.class)
  private YearMonth fechaInscripcion;

  @OneToOne
  @JoinColumn(name = "contacto_id")
  @Cascade(CascadeType.ALL)
  private Contacto contacto;

  @OneToOne
  @JoinColumn(name = "descuento_id")
  private Descuento descuento;

  @ManyToMany
  @JoinTable(
      name = "padres_x_alumno",
      joinColumns = @JoinColumn(name = "alumno_id"),
      inverseJoinColumns = @JoinColumn(name = "padre_id"))
  @JsonIgnoreProperties("alumnos")
  @Cascade(CascadeType.ALL)
  private List<Padre> padresACargo;

  @ManyToMany(mappedBy = "alumnosAnotados")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "alumnosAnotados"})
  private List<Clase> clases;

  @OneToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

}
