package com.houndsoft.towerbridge.services.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class UsuarioYaExistenteException extends CustomException {
    public UsuarioYaExistenteException(String username) {
        super(getMessage(username));
    }

    private static String getMessage(String username) {
        return String.format("No se puede crear / modificar el usuario. El username %s ya esta dado de alta en el sistema.",username);
    }
}
