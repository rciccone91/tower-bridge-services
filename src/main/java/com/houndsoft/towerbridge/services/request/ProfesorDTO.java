package com.houndsoft.towerbridge.services.request;

import com.houndsoft.towerbridge.services.model.Contacto;
import com.houndsoft.towerbridge.services.model.Profesor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

@Builder @Data
public class ProfesorDTO implements Serializable {

    @NotNull
    @NotBlank(message = "Se debe ingresar nombre y apellido")
    private String nombreApellido;

    @NotNull
    private Integer dni;

    @NotNull(message = "Se debe ingresarla edad")
    private Integer edad;

    @Builder.Default
    private String detalles = null;

    @NotNull
    @NotBlank(message = "Se debe ingresar los datos de cuenta bancaria")
    private String cbuCvu;

    @NotNull
    @NotBlank(message = "Se debe ingresar los datos de experiencia previa")
    private String experienciaPrevia;

    @Builder.Default
    private Boolean valorHoraDiferenciado = false;

    @Builder.Default
    private Optional<Long> contactoId = Optional.empty();

    @NotNull
    @NotBlank(message = "Se debe ingresar el domicilio")
    private String domicilio;

    @NotNull
    @NotBlank(message = "Se debe ingresar el numero de telefono")
    private String telefono;

    @NotNull
    @NotBlank(message = "Se debe ingresar el mail")
    @Email(message = "el mail ingresado debe tener un formato valido")
    private String email;

    private Contacto buildContacto(){
        Contacto contacto = new Contacto();
        contactoId.ifPresent(contacto::setId);
        contacto.setDomicilio(this.domicilio);
        contacto.setEmail(this.email);
        contacto.setTelefono(this.telefono);
        return contacto;
    }

    public Profesor buildProfesor() {
        Profesor profesor = new Profesor();
        profesor.setCbuCvu(this.cbuCvu);
        profesor.setDetalles(this.detalles);
        profesor.setValorHoraDiferenciado(this.valorHoraDiferenciado);
        profesor.setContacto(this.buildContacto());
        profesor.setDni(this.dni);
        profesor.setEdad(this.edad);
        profesor.setExperienciaPrevia(this.experienciaPrevia);
        profesor.setNombreApellido(this.nombreApellido);
//        profesor.setUsuario(usuario2);
        return profesor;
    }

}
