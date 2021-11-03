package com.houndsoft.towerbridge.services.request;

import com.houndsoft.towerbridge.services.model.Curso;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CursoDTO {
    private String nombre;
    private String descripcion;
    private Curso.TipoDeCurso tipoDeCurso;
    private List<Long> librosDelCurso;
    private Integer valorArancel;
    private Integer valorExamen;
    private Integer valorHoraProfesor;

    public Curso buildCurso() {
        return Curso.builder().nombre(nombre).descripcion(descripcion).tipoDeCurso(tipoDeCurso).valorArancel(valorArancel).valorExamen(valorExamen).valorHoraProfesor(valorHoraProfesor).build();
    }
}
