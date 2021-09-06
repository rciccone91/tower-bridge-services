package com.houndsoft.towerbridge.services.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "materiales")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Material extends AbstractEntity {

    @Column(nullable = false)
    private String nombre;
    @Column(length = 500)
    private String detalle;
    @Column(nullable = false)
    private Integer stock;
}
