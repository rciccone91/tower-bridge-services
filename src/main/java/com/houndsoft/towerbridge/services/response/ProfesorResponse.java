package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Profesor;
import lombok.*;

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

    public static ProfesorResponse buildFromProfesor(Profesor profesor) {
        return ProfesorResponse.builder().nombreApellido(profesor.getNombreApellido()).dni(profesor.getDni())
                .fechaDeNacimiento(profesor.getFechaDeNacimiento()).detalles(profesor.getDetalles()).cbuCvu(profesor.getCbuCvu())
                .experienciaPrevia(profesor.getExperienciaPrevia()).valorHoraDiferenciado(profesor.getValorHoraDiferenciado())
                .domicilio(profesor.getContacto().getDomicilio()).telefono(profesor.getContacto().getTelefono())
                .email(profesor.getContacto().getEmail()).contactoId(profesor.getContacto().getId()).build();
    }
}
