package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Clase;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private Map<String,Object> curso;
  private Map<String,Object> profesor;
  private List<Map<String,Object>> alumnos;

  public static ClaseResponse buildFromClase(Clase clase) {

    List<Map<String,Object>> alumnosList = new ArrayList<>();
    clase.getAlumnosAnotados().forEach(a -> {
          Map<String,Object> alumno = new HashMap<>();
          alumno.put("id",a.getId());
          alumno.put("nombre",a.getNombreApellido());
          alumnosList.add(alumno);
        });

    Map<String,Object> profesor = new HashMap<>();
    profesor.put("id",clase.getProfesor().getId());
    profesor.put("nombre",clase.getProfesor().getNombreApellido());

    Map<String,Object> curso = new HashMap<>();
    curso.put("id",clase.getCurso().getId());
    curso.put("nombre",clase.getCurso().getNombre());

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
        .curso(curso)
        .profesor(profesor)
        .alumnos(alumnosList)
        .build();
  }
}
