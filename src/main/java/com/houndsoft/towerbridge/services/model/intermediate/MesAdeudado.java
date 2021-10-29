package com.houndsoft.towerbridge.services.model.intermediate;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MesAdeudado {

    private Integer alumnoId;
    private Integer cursoId;
    private Integer valorArancel;
}
