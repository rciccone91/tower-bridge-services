package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Descuento;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoResponse {

    private String nombreApellido;
    private String fechaDeNacimiento;
    private Integer dni;
    private String anioEscolar;
    private String colegio;
    private String nivelIngles;
    private String institucionesPrevias;
    private String detalles;
    private Boolean rindeExamen;
//    private String usuario;
    private String domicilio;
    private String telefono;
    private String email;
    private List<String> padres;
    private List<String> clases;
    private String descuentoAplicado;


    public static AlumnoResponse buildFromAlumno(Alumno alumno) {
        if(alumno.isPersisted()){
            final List<String> padres = alumno.getPadresACargo().stream().distinct()
                    .map(p -> String.format("%s - Contacto: %s ", p.getNombreApellido(), p.getContacto().getTelefono())).collect(Collectors.toList());

            final List<String> clases = alumno.getClases().stream()
                    .map(c -> String.format("%s- %s %s a %s", c.getNombre(), c.getDia(), c.getHorarioInicio(), c.getHorarioFin())).collect(Collectors.toList());

            final Descuento descuento = alumno.getDescuento();

            final String nombreDescuento = descuento != null ? descuento.getNombre() : "Ninguno";
            return AlumnoResponse.builder().anioEscolar(alumno.getAnioEscolar()).colegio(alumno.getColegio()).detalles(alumno.getDetalles())
                    .dni(alumno.getDni()).domicilio(alumno.getContacto().getDomicilio()).email(alumno.getContacto().getEmail()).telefono(alumno.getContacto().getTelefono())
                    .fechaDeNacimiento(alumno.getFechaDeNacimiento()).institucionesPrevias(alumno.getInstitucionesPrevias()).nivelIngles(alumno.getNivelIngles())
                    .nombreApellido(alumno.getNombreApellido()).rindeExamen(alumno.getRindeExamen())
                    .padres(padres).clases(clases).descuentoAplicado(nombreDescuento).build();
        } else throw new RuntimeException("El alumno no existe.");
    }
}
