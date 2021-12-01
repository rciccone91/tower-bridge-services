package com.houndsoft.towerbridge.services.exception;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.model.Curso;

import java.util.List;

public class ClasesExistentesParaCursoException extends CustomException {

    public ClasesExistentesParaCursoException(Curso curso, Clase clase) {
        super(getMessage(curso,clase));
    }

    //~ Methods ......................................................................................................................................

    private static String getMessage(Curso curso, Clase clase) {
        return String.format("No se puede eliminar el curso %s. La clase %s actualmente corresponde a este curso",curso.getNombre(), clase.getNombre());
    }
}
