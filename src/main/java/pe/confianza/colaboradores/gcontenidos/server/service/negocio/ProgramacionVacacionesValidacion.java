package pe.confianza.colaboradores.gcontenidos.server.service.negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametrosConstants;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Component
public class ProgramacionVacacionesValidacion {

	private static Logger logger = LoggerFactory.getLogger(ProgramacionVacacionesValidacion.class);

	@Autowired
	private VacacionProgramacionDao vacacionProgramacionDao;

	@Autowired
	private ParametrosConstants parametrosConstants;

	public List<VacacionProgramacion> ordernarTramos(List<VacacionProgramacion> programaciones) {
		programaciones.sort(Comparator.comparing(VacacionProgramacion::getOrden));
		return programaciones;
	}

	public void validarRangoFechas(VacacionProgramacion programacion) {
		logger.info("[BEGIN] validarRangoFechas");
		if (programacion.getFechaInicio().isAfter(programacion.getFechaFin()))
			throw new AppException("La fecha de inicio no puede ser mayor a la fecha fin");
		logger.info("[END] validarRangoFechas");
	}

	public void validarTramoVacaciones(VacacionProgramacion programacion) {
		logger.info("[BEGIN] validarTramoVacaciones");
		List<VacacionProgramacion> programacionesRegistradas = vacacionProgramacionDao.findByUsuarioBTAndIdEstado(
				programacion.getPeriodo().getEmpleado().getUsuarioBT(), EstadoVacacion.REGISTRADO.id);
		programacionesRegistradas = ordernarTramos(programacionesRegistradas);

		int diasAcumuladosVacaciones = 0;
		int contadorSabados = 0;
		int contadorDomingos = 0;
		for (VacacionProgramacion vacacionProgramacion : programacionesRegistradas) {
			if (Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(),
					programacion.getFechaInicio())) {
				throw new AppException("Cambie la fecha de inicio");
			}
			if (Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(),
					programacion.getFechaFin())) {
				throw new AppException("Cambie la fecha de fin");
			}
			diasAcumuladosVacaciones += Utilitario.obtenerDiferenciaDias(vacacionProgramacion.getFechaInicio(),
					vacacionProgramacion.getFechaFin());
			contadorSabados += Utilitario.obtenerCantidadSabados(vacacionProgramacion.getFechaInicio(),
					vacacionProgramacion.getFechaFin());
			contadorDomingos += Utilitario.obtenerCantidadDomingos(vacacionProgramacion.getFechaInicio(),
					vacacionProgramacion.getFechaFin());
		}
		int diasProgramacion = Utilitario.obtenerDiferenciaDias(programacion.getFechaInicio(),
				programacion.getFechaFin());

		if (diasAcumuladosVacaciones == 0 && diasProgramacion < 7) {
			throw new AppException("Debe tener 7 dias como mínimo");
		}
		if (diasAcumuladosVacaciones == 7 && diasProgramacion < 8) {
			throw new AppException("Debe tener 8 dias como mínimo");
		}
		if (diasAcumuladosVacaciones == 8 && diasProgramacion < 7) {
			throw new AppException("Debe tener 7 dias como mínimo");
		}
		contadorSabados += Utilitario.obtenerCantidadSabados(programacion.getFechaInicio(), programacion.getFechaFin());
		if (contadorSabados > 4) {
			throw new AppException("No debe haber más de 4 sábados");
		}
		contadorDomingos += Utilitario.obtenerCantidadDomingos(programacion.getFechaInicio(),
				programacion.getFechaFin());
		if (contadorDomingos > 4) {
			throw new AppException("No debe haber más de 4 domingos");
		}
		logger.info("[END] validarTramoVacaciones");
	}

	public void validarEmpleadoNuevo(VacacionProgramacion programacion, Empleado empleado) {
		logger.info("[BEGIN] validarEmpleadoNuevo");
		LocalDate fechaParaPedirVacacion = empleado.getFechaIngreso().plusMonths(6);
		logger.info("Empleado -> fecha ingreso: {}", new Object[] { fechaParaPedirVacacion });
		if (programacion.getFechaInicio().isBefore(fechaParaPedirVacacion))
			throw new AppException("Debe tener una antiguedad de 6 meses");
		logger.info("[END] validarEmpleadoNuevo");
	}

	public VacacionProgramacion obtenerOrdenProgramacion(VacacionProgramacion programacion, String usuarioModifica) {
		logger.info("[BEGIN] obtenerOrdenProgramacion");
		List<VacacionProgramacion> programacionesRegistradas = vacacionProgramacionDao.findByUsuarioBTAndIdEstado(
				programacion.getPeriodo().getEmpleado().getUsuarioBT(), EstadoVacacion.REGISTRADO.id);
		if (programacionesRegistradas.isEmpty()) {
			programacion.setOrden(1);
		} else {
			Comparator<VacacionProgramacion> odenPorFechas = new Comparator<VacacionProgramacion>() {
				@Override
				public int compare(VacacionProgramacion prog1, VacacionProgramacion prog2) {
					return prog1.getFechaInicio().compareTo(prog2.getFechaInicio());
				}
			};
			programacionesRegistradas.add(programacion);
			programacionesRegistradas.sort(odenPorFechas);
			for (int i = 0; i < programacionesRegistradas.size(); i++) {
				programacionesRegistradas.get(i).setOrden(i + 1);
				if (programacionesRegistradas.get(i).getId() != null) {
					programacionesRegistradas.get(i).setUsuarioModifica(usuarioModifica);
					programacionesRegistradas.get(i).setFechaModifica(LocalDate.now());
					vacacionProgramacionDao.save(programacionesRegistradas.get(i));
				} else {
					programacion = programacionesRegistradas.get(i);
				}
			}
		}
		logger.info("[END] obtenerOrdenProgramacion");
		return programacion;
	}

	public void validarFechaRegistroProgramacion(LocalDate fechaInicioVacacion) {
		logger.info("[BEGIN] validarFechaRegistroProgramacion");
		LocalDate ahora = LocalDate.now();
		if (fechaInicioVacacion.isBefore(ahora))
			throw new AppException("Fecha de inicio no puede ser una fecha anterior a la de hoy");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String valorFechaInicio = parametrosConstants.FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES;
		if (valorFechaInicio.isEmpty())
			throw new AppException("No existe el parámetro para inicio de registro de programación de vacaciones");
		String strFechaInicioRegistroProgramacion = valorFechaInicio.trim() + "/" + ahora.getYear();
		if (ahora.isBefore(LocalDate.parse(strFechaInicioRegistroProgramacion, formatter)))
			throw new AppException("No se puede registrar antes de " + strFechaInicioRegistroProgramacion);
		String valorFechaFin = parametrosConstants.FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES;
		if (valorFechaFin.isEmpty())
			throw new AppException("No existe el parámetro para fin de registro de programación de vacaciones");
		String strFechaFinRegistroProgramacion = valorFechaFin.trim() + "/" + ahora.getYear();
		if (ahora.isAfter(LocalDate.parse(strFechaFinRegistroProgramacion, formatter)))
			throw new AppException("No se puede registrar después de " + strFechaFinRegistroProgramacion);
		logger.info("[END] validarFechaRegistroProgramacion");
	}

}
