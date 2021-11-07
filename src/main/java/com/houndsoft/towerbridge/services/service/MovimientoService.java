package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.exception.CobroInvalidoException;
import com.houndsoft.towerbridge.services.model.*;
import com.houndsoft.towerbridge.services.repository.*;
import com.houndsoft.towerbridge.services.request.MovimientoDTO;
import com.houndsoft.towerbridge.services.utils.Utils;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.MonthDay;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static com.houndsoft.towerbridge.services.utils.Utils.getFromDate;
import static com.houndsoft.towerbridge.services.utils.Utils.getMonthSpanish;

@Service
public class MovimientoService {

  @Autowired MovimientoRepository movimientoRepository;
  @Autowired CajaRepository cajaRepository;
  @Autowired AlumnoRepository alumnoRepository;
  @Autowired UsuarioRepository usuarioRepository;
  @Autowired CursoRepository cursoRepository;

  public Movimiento createMovimiento(MovimientoDTO movimientoDTO) {
    // TODO - separar logica dependiendo de COBRO / PAGO / MOVIMIENTO MANUAL
    Optional<Curso> curso = cursoRepository.findById(movimientoDTO.getCursoId());
    Usuario usuario = usuarioRepository.getById(movimientoDTO.getUsuarioId());
    Optional<Alumno> alumno = alumnoRepository.findById(movimientoDTO.getAlumnosId());
    YearMonth yearMonth = getFromDate(movimientoDTO.getMesAbonado());
    if (curso.isEmpty() || alumno.isEmpty()) {
      throw new RuntimeException(
          "Hubo un error con el alumno y/o curso al registrar el cobro, contacte al administrador.");
    }
    validarCusoParaAlumno(alumno.get(), movimientoDTO);
    validarCobroYaRealizado(curso.get(), alumno.get(), yearMonth);
    validarMontoDeCobro(curso.get(), alumno.get(), movimientoDTO, yearMonth);
    Movimiento movimiento = movimientoDTO.buildMovimientoCobro(curso.get(),alumno.get());
    movimientoRepository.save(movimiento);
    movimiento.setUsuario(usuario);
    movimientoRepository.save(movimiento);
    updateCaja(movimiento);

    //TODO - update caja
    return movimiento;
  }

  private void updateCaja(Movimiento movimiento) {
    final Caja caja = cajaRepository.findFirstByOrderByFechaCreacionDesc();
    final int valorActual;
    if(movimiento.getTipoMovimiento().equals(Movimiento.TipoDeMovimiento.COBRO) || movimiento.getTipoMovimiento().equals(Movimiento.TipoDeMovimiento.ENTRADA_MANUAL)){
      valorActual = caja.getValorActual() + movimiento.getMonto();
    } else {
      valorActual = caja.getValorActual() - movimiento.getMonto();
    }
    Caja updatedCaja = Caja.builder().ultimoMovimiento(movimiento).valorActual(valorActual).build();
    cajaRepository.save(updatedCaja);
  }

  private void validarMontoDeCobro(
      Curso curso, Alumno alumno, MovimientoDTO movimientoDTO, YearMonth mesArancel) {
    final MonthDay diaDelMes = MonthDay.now();
    double montoCobro = new Double(curso.getValorArancel());
    if (alumno.getDescuento() != null) {
      final double porcentajeDesc = (double) (alumno.getDescuento().getPorcentaje() / 100);
      montoCobro = montoCobro * (1 - porcentajeDesc);
    }
    YearMonth currentMonth = getFromDate(new Date());
    if (mesArancel.isBefore(currentMonth)) {
      final double porcentaje = 1.2;
      montoCobro = montoCobro * porcentaje;
    } else {
      if (diaDelMes.isAfter(MonthDay.of(diaDelMes.getMonth(), 15))
          && diaDelMes.isBefore(MonthDay.of(diaDelMes.getMonth(), 20))) {
        montoCobro = montoCobro * 1.1;
      } else if (diaDelMes.isAfter(MonthDay.of(diaDelMes.getMonth(), 20))) {
        montoCobro = montoCobro * 1.2;
      }
    }
    final double valorDeCobroIngresado = new Double(movimientoDTO.getMonto());
    if(montoCobro != valorDeCobroIngresado){
      throw new CobroInvalidoException(
              String.format(
                      "El monto ingrsado para el cobro es incorrecto. El mismo debe ser %s.",
                      montoCobro));
    }
  }

  private void validarCobroYaRealizado(Curso curso, Alumno alumno, YearMonth yearMonth) {
    final Movimiento movimiento =
        movimientoRepository.findByAlumnoEqualsAndCursoEqualsAndMesAbonadoEquals(
            alumno, curso, yearMonth);
    if (movimiento != null) {
      throw new CobroInvalidoException(
          String.format(
              "El arancel del alumno %s para el curso %s en el mes %s ya fue registrado",
              alumno.getNombreApellido(), curso.getNombre(), getMonthSpanish(yearMonth)));
    }
  }

  private void validarCusoParaAlumno(Alumno alumno, MovimientoDTO movimientoDTO) {
    if (alumno.getClases().stream().noneMatch(c -> c.getCurso().getId().equals(movimientoDTO.getCursoId()))) {
      throw new CobroInvalidoException(
          "El alumno no se encuentra inscripto a ninguna clase de ese curso");
    }
  }

  public void softDeleteMovimiento(long id) {}
}
