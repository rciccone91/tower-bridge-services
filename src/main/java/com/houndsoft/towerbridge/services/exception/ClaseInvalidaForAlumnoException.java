package com.houndsoft.towerbridge.services.exception;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Profesor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class ClaseInvalidaForAlumnoException extends CustomException {

    public ClaseInvalidaForAlumnoException(Alumno alumno, String claseNombre, String dia, Integer inicio, Integer fin) {
        super(getMessage(alumno,claseNombre,dia,inicio,fin));
    }

    //~ Methods ......................................................................................................................................

    private static String getMessage(Alumno alumno, String claseNombre, String dia, Integer inicio, Integer fin) {
        return String.format("Error al guardar los datos de la clase. El alumno %s ya se encuentra cursando una clase en el mismo día y rango horario: %s",
                alumno.getNombreApellido(), String.format("%s - %s de %s a %s",claseNombre,dia,inicio,fin));
    }
}
