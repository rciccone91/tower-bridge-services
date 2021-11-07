package com.houndsoft.towerbridge.services.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class AlumnoInscriptoEnClaseException extends CustomException {

    public AlumnoInscriptoEnClaseException(String alumno, String clase) {
        super(getMessage(alumno,clase));
    }

    //~ Methods ......................................................................................................................................

    private static String getMessage(String nombrePadre, String clase) {
        return String.format("No se puede eliminar el alumno %s ya que el mismo esta inscripto en la clase %s.",nombrePadre, clase);
    }
}

