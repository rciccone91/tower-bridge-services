package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Clase;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaseResponse {
  private String nombre;
  private String descripcion;
  private Clase.Dia dia;
  private Integer horarioInicio;
  private Integer horarioFin;
  private String linkVideollamada;
  private String claveVideollamada;
  private String linkClassroom;
  private String claveClassroom;
  private String curso;
  private String profesor;
  private List<String> alumnos;

  public static ClaseResponse buildFromClase(Clase clase) {

    List<String> alumnos =
        clase.getAlumnosAnotados().stream()
            .map(a -> String.format("%s - %s", a.getNombreApellido(), a.getDni()))
            .collect(Collectors.toList());

    return ClaseResponse.builder()
        .claveClassroom(clase.getClaveClassroom())
        .claveVideollamada(clase.getClaveVideollamada())
        .descripcion(clase.getDescripcion())
        .dia(clase.getDia())
        .horarioFin(clase.getHorarioFin())
        .horarioInicio(clase.getHorarioInicio())
        .nombre(clase.getNombre())
        .linkClassroom(clase.getLinkClassroom())
        .linkVideollamada(clase.getLinkVideollamada())
        .curso(clase.getCurso().getNombre())
        .profesor(clase.getProfesor().getNombreApellido())
        .alumnos(alumnos)
        .build();
  }
}
