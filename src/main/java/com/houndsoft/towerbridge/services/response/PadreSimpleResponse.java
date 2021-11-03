package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Padre;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PadreSimpleResponse {

    private Long id;
    private String nombre;
    private String telefono;

    public static PadreSimpleResponse buildFromPadre(Padre padre) {
        final PadreSimpleResponseBuilder builder = PadreSimpleResponse.builder().id(padre.getId()).nombre(padre.getNombreApellido());
        if(padre.getContacto() != null){
            return builder.telefono(padre.getContacto().getTelefono()).build();
        } else return builder.build();
    }
}
