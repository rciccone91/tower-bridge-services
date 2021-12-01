package com.houndsoft.towerbridge.services.request;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Contacto;
import com.houndsoft.towerbridge.services.utils.Utils;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Builder
@Data
public class AlumnoDTO {

  private String nombreApellido;
  private String fechaDeNacimiento;
  private Integer dni;
  private String anioEscolar;
  private String colegio;
  private String nivelIngles;
  private String institucionesPrevias;
  private String detalles;
  private Boolean rindeExamen;
  private List<Long> padresACargo;
  @Builder.Default private Optional<Long> contactoId = Optional.empty();
  private Long usuarioId;
  private String domicilio;
  private String telefono;
  private String email;
  private Date fechaInscripcion;

  private Contacto buildContacto() {
    Contacto contacto = new Contacto();
    contactoId.ifPresent(contacto::setId);
    contacto.setDomicilio(this.domicilio);
    contacto.setEmail(this.email);
    contacto.setTelefono(this.telefono);
    return contacto;
  }

  public Alumno buildAlumno() {
    Alumno alumno = new Alumno();
    alumno.setContacto(this.buildContacto());
    alumno.setNombreApellido(this.nombreApellido);
    alumno.setFechaDeNacimiento(this.fechaDeNacimiento);
    alumno.setAnioEscolar(this.anioEscolar);
    alumno.setColegio(this.colegio);
    alumno.setDni(this.dni);
    alumno.setDetalles(this.detalles);
    alumno.setInstitucionesPrevias(this.institucionesPrevias);
    alumno.setNivelIngles(nivelIngles);
    alumno.setRindeExamen(this.rindeExamen);
    alumno.setFechaInscripcion(Utils.getYearMonthFromDate(fechaInscripcion));
    return alumno;
  }
}
