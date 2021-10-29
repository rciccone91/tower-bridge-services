package com.houndsoft.towerbridge.services.model;

import com.houndsoft.towerbridge.services.converters.YearMonthDateAttributeConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.YearMonth;
import java.util.Date;

@Entity(name = "movimientos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento extends AbstractEntity {

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoDeMovimiento tipoMovimiento;

  @Column(nullable = false)
  private Integer monto;

  @Column(nullable = false)
  @NotEmpty
  private String detalle;

  @Column(nullable = false)
  private Date fecha;

  @Convert(converter = YearMonthDateAttributeConverter.class)
  private YearMonth mesAbonado;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MedioDePago medioDePago;

  @OneToOne
  @JoinColumn(name = "alumno_id")
  private Alumno alumno;

  @OneToOne
  @JoinColumn(name = "curso_id")
  private Curso curso;

  @OneToOne
  @JoinColumn(name = "proveedor_id")
  private Proveedor proveedor;

  @OneToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  public enum TipoDeMovimiento {
    COBRO,
    PAGO,
    ENTRADA_MANUAL,
    SALIDA_MANUAL
  }

  public enum MedioDePago {
    EFECTIVO,
    TRANSFERENCIA_BANCARIA,
    MERCADO_PAGO
  }
}
