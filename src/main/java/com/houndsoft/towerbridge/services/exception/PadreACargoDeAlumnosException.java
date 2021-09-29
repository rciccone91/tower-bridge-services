package com.houndsoft.towerbridge.services.exception;

public class PadreACargoDeAlumnosException extends CustomException {

    public PadreACargoDeAlumnosException(String nombrePadre) {
        super(getMessage(nombrePadre));
    }

    //~ Methods ......................................................................................................................................

    private static String getMessage(String nombrePadre) {
        return String.format("No se puede eliminar el padre %s ya que el mismo tiene alumnos a su cargo.",nombrePadre);
    }
}
