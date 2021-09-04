package com.houndsoft.towerbridge.services.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@ControllerAdvice
@Slf4j
public final class GeneralExceptionHandlingController {

    //~ Methods ......................................................................................................................................


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnknownException(@NotNull final Exception exception) {
        final String format = String.format("Un error inesperado ocurrió: %s", exception.getMessage());
        log.error(format, exception);
        return new ResponseEntity<>(format, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(@NotNull final EntityNotFoundException entityNotFoundException) {
        final String format = String.format("El recurso requerido no pudo ser encontrado. Message: %s", entityNotFoundException.getMessage());
        log.error(format, entityNotFoundException);
        return new ResponseEntity<>(format, HttpStatus.NOT_FOUND);
    }

}
