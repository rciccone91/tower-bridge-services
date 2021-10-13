package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Padre;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PadreResponse {

    private String nombreApellido;
    private Integer dni;
    private String detalles;
    private Long contactoId;
    private String domicilio;
    private String telefono;
    private String email;
    private List<String> alumnosACargo;

    public static PadreResponse buildFromPadre(Padre padre) {
        final List<String> alumnos = padre.getAlumnos().stream()
                .map(a -> String.format("%s - DNI: %s ", a.getNombreApellido(), a.getDni())).collect(Collectors.toList());
        return PadreResponse.builder().contactoId(padre.getContacto().getId()).detalles(padre.getDetalles())
                .dni(padre.getDNI()).domicilio(padre.getContacto().getDomicilio())
                .email(padre.getContacto().getEmail()).telefono(padre.getContacto().getTelefono())
                .nombreApellido(padre.getNombreApellido()).alumnosACargo(alumnos).build();
    }
}
