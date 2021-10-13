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
    private String nombreApellido;

    public static PadreSimpleResponse buildFromPadre(Padre padre) {
        return PadreSimpleResponse.builder().id(padre.getId()).nombreApellido(padre.getNombreApellido()).build();
    }
}
