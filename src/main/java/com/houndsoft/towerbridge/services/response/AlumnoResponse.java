package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Descuento;
import com.houndsoft.towerbridge.services.utils.AlphabeticalOrder;
import com.houndsoft.towerbridge.services.utils.Utils;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoResponse {

    private Long id;
    private String nombreApellido;
    private String fechaDeNacimiento;
    private Integer dni;
    private String anioEscolar;
    private String colegio;
    private String nivelIngles;
    private String institucionesPrevias;
    private String detalles;
    private Boolean rindeExamen;
    private Map<String,Object> usuario;
    private String domicilio;
    private String telefono;
    private String email;
    private List<PadreSimpleResponse> padres;
    private List<String> clases;
    private String descuentoAplicado;
    private Date fechaInscripcion;


    public static AlumnoResponse buildFromAlumno(Alumno alumno) {
        if(alumno.isPersisted()){
            Map<String,Object> usuarioMap = null;
            if(alumno.getUsuario()!= null){
                usuarioMap = new HashMap<>();
                usuarioMap.put("id",alumno.getUsuario().getId());
                usuarioMap.put("username",alumno.getUsuario().getUsername());
            }

            final List<PadreSimpleResponse> padres = alumno.getPadresACargo().stream().distinct().map(PadreSimpleResponse::buildFromPadre).sorted(AlphabeticalOrder.PADRES_ALPHABETICAL_ORDER).collect(Collectors.toList());
            final List<String> clases = alumno.getClases().stream()
                    .map(c -> String.format("%s- %s %s a %s", c.getNombre(), c.getDia(), c.getHorarioInicio(), c.getHorarioFin())).collect(Collectors.toList());

            final Descuento descuento = alumno.getDescuento();

            final String nombreDescuento = descuento != null ? descuento.getNombre() : "Ninguno";
            return AlumnoResponse.builder().anioEscolar(alumno.getAnioEscolar()).colegio(alumno.getColegio()).detalles(alumno.getDetalles())
                    .dni(alumno.getDni()).domicilio(alumno.getContacto().getDomicilio()).email(alumno.getContacto().getEmail()).telefono(alumno.getContacto().getTelefono())
                    .fechaDeNacimiento(alumno.getFechaDeNacimiento()).institucionesPrevias(alumno.getInstitucionesPrevias()).nivelIngles(alumno.getNivelIngles())
                    .nombreApellido(alumno.getNombreApellido()).rindeExamen(alumno.getRindeExamen()).id(alumno.getId()).fechaInscripcion(Utils.getDateFromYearMonth(alumno.getFechaInscripcion()))
                    .padres(padres).clases(clases).descuentoAplicado(nombreDescuento).usuario(usuarioMap).build();
        } else throw new RuntimeException("El alumno no existe.");
    }
}
