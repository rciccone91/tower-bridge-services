package com.houndsoft.towerbridge.services.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;


@EqualsAndHashCode(callSuper = false)
@Value
public class ProfesorAsignadoAClaseException extends CustomException {

    public ProfesorAsignadoAClaseException(String nombreProfesor) {
        super(getMessage(nombreProfesor));
    }

    //~ Methods ......................................................................................................................................

    private static String getMessage(String nombreProfesor) {
        return String.format("No se puede eliminar el profesor %s ya que el mismo está asignado a una clase.",nombreProfesor);
    }
}
