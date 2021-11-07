package com.houndsoft.towerbridge.services.exception;

import com.houndsoft.towerbridge.services.model.Alumno;

public class CobroInvalidoException extends CustomException {

    public CobroInvalidoException(String message) {
        super(getMessage(message));
    }

    //~ Methods ......................................................................................................................................

    private static String getMessage(String message) {
        return String.format("Error al registrar el cobro. %s",message);
    }
}
