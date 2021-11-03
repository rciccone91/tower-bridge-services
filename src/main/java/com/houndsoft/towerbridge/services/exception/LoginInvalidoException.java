package com.houndsoft.towerbridge.services.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class LoginInvalidoException extends CustomException {

    public LoginInvalidoException() {
        super(getErrorMessage());
    }

    //~ Methods ......................................................................................................................................

    static String getErrorMessage() {
        return "No se pudo realizar el login. Por favor, verifica que el usuario y la clave sean correctas.";
    }

}
