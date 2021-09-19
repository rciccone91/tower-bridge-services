package com.houndsoft.towerbridge.services.handler;

import com.houndsoft.towerbridge.services.exception.CustomException;
import com.houndsoft.towerbridge.services.response.ErrorResponse;
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

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(@NotNull final CustomException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        log.error(errorResponse.getErrorMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(@NotNull final Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(String.format("Un error inesperado ocurrió: %s", exception.getMessage()));
        log.error(errorResponse.getErrorMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(@NotNull final EntityNotFoundException entityNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(String.format("El recurso requerido no pudo ser encontrado. Message: %s", entityNotFoundException.getMessage()));
        log.error(errorResponse.getErrorMessage(), entityNotFoundException);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
