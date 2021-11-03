package com.houndsoft.towerbridge.services.request;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.repository.CursoRepository;
import lombok.Builder;
import lombok.Data;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Builder
@Data
public class ClaseDTO {

  private Long curso;
  private String nombre;
  private String descripcion;
  private String dia;
  private Integer horarioInicio;
  private Integer horarioFin;
  private String linkVideollamada;
  private String claveVideollamada;
  private String linkClassroom;
  private String claveClassroom;
  private List<Long> alumnosIds;
  private Long profesorId;

  public Clase buildClase() {
    return Clase.builder()
        .nombre(nombre)
        .descripcion(descripcion)
        .dia(Clase.Dia.valueOf(dia))
        .horarioInicio(horarioInicio)
        .horarioFin(horarioFin)
        .linkVideollamada(linkVideollamada)
        .linkClassroom(linkClassroom)
        .claveVideollamada(claveVideollamada)
        .claveClassroom(claveClassroom)
        .build();
  }
}
