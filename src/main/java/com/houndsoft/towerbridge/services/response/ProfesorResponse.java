package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Profesor;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorResponse {

    private String nombreApellido;
    private Integer dni;
    private String fechaDeNacimiento;
    private String detalles;
    private String cbuCvu;
    private String experienciaPrevia;
    private Boolean valorHoraDiferenciado;
    private Long contactoId;
    private String domicilio;
    private String telefono;
    private String email;
    private Map<String,Object> usuario;
    private List<String> clases;

    public static ProfesorResponse buildFromProfesor(Profesor profesor, List<String> clases) {
        Map<String,Object> usuarioMap = null;
        if(profesor.getUsuario()!= null){
            usuarioMap = new HashMap<>();
            usuarioMap.put("id",profesor.getUsuario().getId());
            usuarioMap.put("username",profesor.getUsuario().getUsername());
        }
        return ProfesorResponse.builder().nombreApellido(profesor.getNombreApellido()).dni(profesor.getDni())
                .fechaDeNacimiento(profesor.getFechaDeNacimiento()).detalles(profesor.getDetalles()).cbuCvu(profesor.getCbuCvu())
                .experienciaPrevia(profesor.getExperienciaPrevia()).valorHoraDiferenciado(profesor.getValorHoraDiferenciado())
                .domicilio(profesor.getContacto().getDomicilio()).telefono(profesor.getContacto().getTelefono())
                .email(profesor.getContacto().getEmail()).contactoId(profesor.getContacto().getId())
                .usuario(usuarioMap)
                .clases(clases)
                .build();
    }
}
