package com.houndsoft.towerbridge.services.request;

import com.houndsoft.towerbridge.services.model.Contacto;
import com.houndsoft.towerbridge.services.model.Padre;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Builder
@Data
public class PadreDTO {

    @NotNull
    @NotBlank(message = "Se debe ingresar nombre y apellido")
    private String nombreApellido;

    @NotNull
    private Integer dni;

    @Builder.Default
    private String detalles = null;

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

    public Padre buildPadre() {
        Padre padre = new Padre();
        padre.setContacto(this.buildContacto());
        padre.setDetalles(detalles);
        padre.setDNI(dni);
        padre.setNombreApellido(nombreApellido);
        return padre;
    }
}
