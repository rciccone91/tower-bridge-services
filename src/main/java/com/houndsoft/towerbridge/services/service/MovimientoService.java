package com.houndsoft.towerbridge.services.service;

import com.houndsoft.towerbridge.services.exception.CobroInvalidoException;
import com.houndsoft.towerbridge.services.model.*;
import com.houndsoft.towerbridge.services.model.Movimiento.TipoDeMovimiento;
import com.houndsoft.towerbridge.services.repository.*;
import com.houndsoft.towerbridge.services.request.MovimientoDTO;
import com.houndsoft.towerbridge.services.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static com.houndsoft.towerbridge.services.utils.Utils.*;

@Service
public class MovimientoService {

  @Autowired MovimientoRepository movimientoRepository;
  @Autowired CajaRepository cajaRepository;
  @Autowired AlumnoRepository alumnoRepository;
  @Autowired UsuarioRepository usuarioRepository;
  @Autowired CursoRepository cursoRepository;
  @Autowired ClaseRepository claseRepository;
  @Autowired ProveedorRepository proveedorRepository;

  public Movimiento createMovimiento(MovimientoDTO movimientoDTO) {
    Movimiento movimiento = validateAndGetMovimiento(movimientoDTO);
    Usuario usuario = usuarioRepository.getById(movimientoDTO.getUsuarioId());
    movimientoRepository.save(movimiento);
    movimiento.setUsuario(usuario);
    movimientoRepository.save(movimiento);
    updateCaja(movimiento, false, Optional.empty());
    return movimiento;
  }

  private Movimiento validateAndGetMovimiento(MovimientoDTO movimientoDTO) {
    TipoDeMovimiento tipoDeMovimiento = TipoDeMovimiento.valueOf(movimientoDTO.getTipoMovimiento());
    if (tipoDeMovimiento.equals(TipoDeMovimiento.COBRO)) {
      return validateAndGetCobro(movimientoDTO);
    } else if (tipoDeMovimiento.equals(TipoDeMovimiento.PAGO)) {
      return getPago(movimientoDTO);
    } else return getEntradaManual(movimientoDTO);
  }

  private Movimiento getEntradaManual(MovimientoDTO movimientoDTO) {
    return movimientoDTO.buildEntradaManual();
  }

  private Movimiento getPago(MovimientoDTO movimientoDTO) {
    Optional<Proveedor> proveedor = proveedorRepository.findById(movimientoDTO.getProveedorId());
    if (proveedor.isEmpty()) {
      throw new RuntimeException(
          "Hubo un error con el proveedor al registrar el pago, contacte al administrador.");
    }
    return movimientoDTO.buildMovimientoCobro(proveedor.get());
  }

  private Movimiento validateAndGetCobro(MovimientoDTO movimientoDTO) {
    Optional<Curso> curso = cursoRepository.findById(movimientoDTO.getCursoId());
    Optional<Alumno> alumno = alumnoRepository.findById(movimientoDTO.getAlumnosId());
    YearMonth mesAbonado = getYearMonthFromDate(movimientoDTO.getMesAbonado());
    if (curso.isEmpty() || alumno.isEmpty()) {
      throw new RuntimeException(
          "Hubo un error con el alumno y/o curso al registrar el cobro, contacte al administrador.");
    }
    validarCusoParaAlumno(alumno.get(), movimientoDTO);
    validarMesPrevioAInscripcion(alumno.get(), mesAbonado);
    validarCobroYaRealizado(curso.get(), alumno.get(), mesAbonado);
    validarMontoDeCobro(curso.get(), alumno.get(), movimientoDTO, mesAbonado);
    return movimientoDTO.buildMovimientoCobro(curso.get(), alumno.get());
  }

  private void validarMesPrevioAInscripcion(Alumno alumno, YearMonth mesAbonado) {
    if (mesAbonado.isBefore(alumno.getFechaInscripcion())) {
      throw new CobroInvalidoException(
          String.format(
              "Error al registrar cobro. El arancel para el mes %s  de %s es previo a la inscripción del alumno %s al instituto",
              getMonthSpanish(mesAbonado), mesAbonado.getYear(), alumno.getNombreApellido()));
    }
  }

  private void updateCaja(
      Movimiento movimiento,
      boolean esBorradoDeRegistro,
      Optional<Movimiento> movimientoDeBorrado) {
    final Caja caja = cajaRepository.findFirstByOrderByFechaCreacionDesc();
    final int valorActual;
    final Integer monto = esBorradoDeRegistro ? movimiento.getMonto() * -1 : movimiento.getMonto();
    if (movimiento.getTipoMovimiento().equals(TipoDeMovimiento.COBRO)
        || movimiento.getTipoMovimiento().equals(TipoDeMovimiento.ENTRADA_MANUAL)) {
      valorActual = caja.getValorActual() + monto;
    } else {
      valorActual = caja.getValorActual() - monto;
    }
    Caja updatedCaja = Caja.builder().ultimoMovimiento(movimiento).valorActual(valorActual).build();
    movimientoDeBorrado.ifPresent(caja::setUltimoMovimiento);
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
    YearMonth currentMonth = getYearMonthFromDate(new Date());
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
    if (montoCobro != valorDeCobroIngresado) {
      throw new CobroInvalidoException(
          String.format(
              "El monto ingrsado para el cobro es incorrecto. El mismo debe ser %s.", montoCobro));
    }
  }

  private void validarCobroYaRealizado(Curso curso, Alumno alumno, YearMonth yearMonth) {
    final Movimiento movimiento =
        movimientoRepository.findByAlumnoEqualsAndCursoEqualsAndMesAbonadoEqualsAndActivoTrue(
            alumno, curso, yearMonth);
    if (movimiento != null) {
      throw new CobroInvalidoException(
          String.format(
              "El arancel del alumno %s para el curso %s en el mes %s ya fue registrado",
              alumno.getNombreApellido(), curso.getNombre(), getMonthSpanish(yearMonth)));
    }
  }

  private void validarCusoParaAlumno(Alumno alumno, MovimientoDTO movimientoDTO) {
    if (alumno.getClases().stream()
        .noneMatch(c -> c.getCurso().getId().equals(movimientoDTO.getCursoId()))) {
      throw new CobroInvalidoException(
          "El alumno no se encuentra inscripto a ninguna clase de ese curso");
    }
  }

  public void softDeleteMovimiento(Long id, long usuarioId) {
    final Movimiento retrievedMovimiento = movimientoRepository.getById(id);
    if (retrievedMovimiento.isPersisted()) {
      final Movimiento contraMovimiento = generarContraMovimiento(retrievedMovimiento, usuarioId);
      retrievedMovimiento.setActivo(false);
      movimientoRepository.save(retrievedMovimiento);
      movimientoRepository.save(contraMovimiento);
      updateCaja(retrievedMovimiento, true, Optional.of(contraMovimiento));
    } else throw new RuntimeException("El movimiento no existe.");
  }

  private Movimiento generarContraMovimiento(Movimiento retrievedMovimiento, long usuarioId) {
    final Usuario usuario = usuarioRepository.getById(usuarioId);
    if (retrievedMovimiento.isPersisted()) {
      return Movimiento.builder()
          .tipoMovimiento(retrievedMovimiento.getTipoMovimiento())
          .detalle(retrievedMovimiento.getDetalle())
          .fecha(retrievedMovimiento.getFecha())
          .monto(retrievedMovimiento.getMonto() * -1)
          .medioDePago(retrievedMovimiento.getMedioDePago())
          .proveedor(retrievedMovimiento.getProveedor())
          .alumno(retrievedMovimiento.getAlumno())
          .curso(retrievedMovimiento.getCurso())
          .mesAbonado(retrievedMovimiento.getMesAbonado())
          .usuario(usuario)
          .build();
    } else throw new RuntimeException("El usuario no existe.");
  }

  public List<Movimiento> getLastMovimientos() {
    return movimientoRepository.findTopOrderByFechaCreacionDesc();
  }

  public Caja getEstadoCaja() {
    return cajaRepository.findFirstByOrderByFechaCreacionDesc();
  }

  public List<Movimiento> getMovimientosEntreFechas(
      Date fechaDesde, Date fechaHasta, String tipoMovimiento, Boolean esEntrada) {
    if (esEntrada) {
      List<Movimiento> movimientos =
          new ArrayList<>(
              movimientoRepository
                  .findByTipoMovimientoEqualsAndFechaBetweenAndMontoGreaterThanOrderByFechaDesc(
                      TipoDeMovimiento.ENTRADA_MANUAL, fechaDesde, fechaHasta, 0));
      movimientos.addAll(
          movimientoRepository
              .findByTipoMovimientoEqualsAndFechaBetweenAndMontoGreaterThanOrderByFechaDesc(
                  TipoDeMovimiento.SALIDA_MANUAL, fechaDesde, fechaHasta, 0));
      return movimientos;
    } else if (tipoMovimiento == null) {
      return movimientoRepository.findByFechaBetweenOrderByFechaDesc(fechaDesde, fechaHasta);
    } else {
      return movimientoRepository
          .findByTipoMovimientoEqualsAndFechaBetweenAndMontoGreaterThanOrderByFechaDesc(
              TipoDeMovimiento.valueOf(tipoMovimiento), fechaDesde, fechaHasta, 0);
    }
  }

  public Page<Movimiento> getPaginatedByTipo(TipoDeMovimiento tipoMovimiento, Pageable paging) {
    return movimientoRepository.findByTipoMovimientoEqualsAndActivoTrueAndMontoGreaterThan(
        tipoMovimiento, 0, paging);
  }

  public List<Map<String, Object>> getEstadoDeCuenta(Long alumnoId) {
    Optional<Alumno> maybeAlumno = alumnoRepository.findById(alumnoId);
    if (maybeAlumno.isEmpty()) {
      throw new RuntimeException(
          "Hubo un error con el alumno al buscar el estado de cuenta. Por favor, contacte al administrador.");
    }
    final Alumno alumno = maybeAlumno.get();

    return getEstadoDeCuentaPorAlumno(alumno);
  }

  public List<Map<String, Object>> getEstadoMesCorrientePorAlumno(Alumno alumno) {
    final List<Curso> cursos =
        alumno.getClases().stream().map(Clase::getCurso).collect(Collectors.toList());
    final YearMonth mes = Utils.getYearMonthFromDate(new Date());
    final List<Movimiento> movimientos =
        movimientoRepository
            .findByAlumnoEqualsAndTipoMovimientoEqualsAndActivoTrueAndMontoGreaterThan(
                alumno, TipoDeMovimiento.COBRO, 0);
    final List<Map<String, Object>> response = new ArrayList<>();

    cursos.forEach(
        curso -> {
          final List<Movimiento> movimientosPorCursoPorMes =
              movimientos.stream()
                  .filter(m -> !(m.getMesAbonado().equals(mes) && m.getCurso().equals(curso)))
                  .collect(Collectors.toList());
          final Clase clase =
              alumno.getClases().stream()
                  .filter(cl -> cl.getCurso().equals(curso))
                  .collect(Collectors.toList())
                  .get(0);
          final Map<String, Object> item = new HashMap<>();
          item.put("arancel", String.format("%s %s", getMonthSpanish(mes), mes.getYear()));
          item.put("curso", curso.getNombre());
          item.put("nombre", alumno.getNombreApellido());
          item.put("clase", clase.getNombre());
          final int valorArancelAlumno = getValorArancelAlumno(alumno, curso);
          item.put("valor", valorArancelAlumno);
          if (movimientosPorCursoPorMes.isEmpty()) {
            item.put("estado", "Adeudado");
            item.put("recargo", getRecargo(valorArancelAlumno, mes));
          } else {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(movimientosPorCursoPorMes.get(0).getFecha());
            item.put("fecha", strDate);
            item.put("estado", "Abonado");
            item.put("recargo", getRecargo(valorArancelAlumno, mes));
          }
          response.add(item);
        });
    return response;
  }

  public List<Map<String, Object>> getEstadoDeCuentaPorAlumno(Alumno alumno) {
    final List<Curso> cursos =
        alumno.getClases().stream().map(Clase::getCurso).collect(Collectors.toList());
    List<YearMonth> meses = Utils.getMonthsBetweenDates(alumno.getFechaInscripcion());
    final List<Movimiento> movimientos =
        movimientoRepository
            .findByAlumnoEqualsAndTipoMovimientoEqualsAndActivoTrueAndMontoGreaterThan(
                alumno, TipoDeMovimiento.COBRO, 0);
    final List<Map<String, Object>> response = new ArrayList<>();

    cursos.forEach(
        curso -> {
          for (YearMonth mes : meses) {
            final List<Movimiento> movimientosPorCursoPorMes =
                movimientos.stream()
                    .filter(m -> m.getMesAbonado().equals(mes) && m.getCurso().equals(curso))
                    .collect(Collectors.toList());
            final Map<String, Object> item = new HashMap<>();
            item.put("arancel", String.format("%s %s", getMonthSpanish(mes), mes.getYear()));
            item.put("curso", curso.getNombre());
            item.put("nombre", alumno.getNombreApellido());
            final int valorArancelAlumno = getValorArancelAlumno(alumno, curso);
            item.put("valor", valorArancelAlumno);
            if (movimientosPorCursoPorMes.isEmpty()) {
              item.put("estado", "Adeudado");
              item.put("recargo", getRecargo(valorArancelAlumno, mes));
            } else {
              DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
              String strDate = dateFormat.format(movimientosPorCursoPorMes.get(0).getFecha());
              item.put("fecha", strDate);
              item.put("estado", "Abonado");
              item.put("recargo", getRecargo(valorArancelAlumno, mes));
            }
            response.add(item);
          }
        });
    return response;
  }

  private int getValorArancelAlumno(Alumno alumno, Curso curso) {
    return alumno.getDescuento() == null
        ? curso.getValorArancel()
        : curso.getValorArancel() * (1 - (alumno.getDescuento().getPorcentaje() / 100));
  }

  private Object getRecargo(int valorArancelAlumno, YearMonth mes) {
    if (mes.equals(getCurrentMonthYear())) {
      if (getMonthDayFromDate(new Date()).getDayOfMonth() > 20) {
        return valorArancelAlumno * 0.2;
      } else if (getMonthDayFromDate(new Date()).getDayOfMonth() > 15) {
        return valorArancelAlumno * 0.1;
      } else return 0;
    } else {
      return valorArancelAlumno * 0.2;
    }
  }
}
