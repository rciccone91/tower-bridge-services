package com.houndsoft.towerbridge.services.exception;


import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class UsuarioSinAlumoAsociadoException extends CustomException {
    public UsuarioSinAlumoAsociadoException(String username) {
        super(getMessage(username));
    }

    private static String getMessage(String username) {
        return String.format("No se puede realizar el login. El usuario %s no tiene ningún alumno asociado.",username);
    }
}
