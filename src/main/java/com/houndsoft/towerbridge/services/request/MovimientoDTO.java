package com.houndsoft.towerbridge.services.request;

import com.houndsoft.towerbridge.services.model.Alumno;
import com.houndsoft.towerbridge.services.model.Curso;
import com.houndsoft.towerbridge.services.model.Movimiento;
import com.houndsoft.towerbridge.services.utils.Utils;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class MovimientoDTO {

    private String tipoMovimiento;
    private Integer monto;
    private String detalle;
    private Date fechaDeCobro;
    private Date mesAbonado;
    private String medioDePago;
    private Long alumnosId;
    private Long cursoId;
    private Long proveedorId;
    private Long usuarioId;

    public Movimiento buildMovimiento() {
        return null;
    }

    public Movimiento buildMovimientoCobro(Curso curso, Alumno alumno) {
        return Movimiento.builder().tipoMovimiento(Movimiento.TipoDeMovimiento.valueOf(tipoMovimiento))
                .alumno(alumno).curso(curso).detalle(detalle).fecha(fechaDeCobro).monto(monto)
                .medioDePago(Movimiento.MedioDePago.valueOf(medioDePago)).mesAbonado(Utils.getFromDate(mesAbonado)).build();
    }
}
