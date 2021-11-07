package com.houndsoft.towerbridge.services.exception;

import com.houndsoft.towerbridge.services.model.Alumno;

public class CantidadDeClasesEnSimultaneoInvalidaException extends CustomException {

    public CantidadDeClasesEnSimultaneoInvalidaException(String rango) {
        super(getMessage(rango));
    }

    //~ Methods ......................................................................................................................................

    private static String getMessage(String rango) {
        return "Error al guardar los datos de la clase.Ya hay 3 clases preexistentes en el mismo día y rango horario.";
    }
}
