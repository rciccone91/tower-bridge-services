package com.houndsoft.towerbridge.services.exception;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Profesor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class ClaseInvalidaForProfesorException extends CustomException {

    public ClaseInvalidaForProfesorException(Profesor profesor, String claseNombre, String dia, Integer inicio, Integer fin) {
        super(getMessage(profesor,claseNombre,dia,inicio,fin));
    }

    //~ Methods ......................................................................................................................................

    private static String getMessage(Profesor profesor, String claseNombre, String dia, Integer inicio, Integer fin) {
        return String.format("Error al guardar los datos de la clase. El profesor %s ya se encuentra asignado a una clase en el mismo día y rango horario: %s",
                profesor.getNombreApellido(), String.format("%s - %s de %s a %s",claseNombre,dia,inicio,fin));
    }
}
