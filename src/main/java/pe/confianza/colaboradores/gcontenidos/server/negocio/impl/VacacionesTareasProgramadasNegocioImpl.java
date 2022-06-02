package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.PeriodoVacacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametrosConstants;


@Service
public class VacacionesTareasProgramadasNegocioImpl implements VacacionesTareasProgramadasNegocio {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VacacionesTareasProgramadasNegocioImpl.class);
	
	@Autowired
	private PeriodoVacacionService periodoVacacionService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;
	
	@Autowired
	private ParametrosConstants parametrosConstants;
	
	@Autowired
	private VacacionMetaService vacacionMetaService;

	@Override
	public void actualizarEstadoProgramaciones() {
		LOGGER.info("[BEGIN] actualizarEstadoProgramaciones " + LocalDate.now());
		vacacionProgramacionService.actualizarEstadoProgramaciones();
		LOGGER.info("[END] actualizarEstadoProgramaciones " + LocalDate.now());
	}

	@Override
	public void actualizarPeridos() {
		LOGGER.info("[BEGIN] actualizarPeridos " + LocalDate.now());
		List<Empleado> lstEmpleado = empleadoService.listar();
		for (Empleado empleado : lstEmpleado) {
			periodoVacacionService.actualizarPeriodos(empleado, "TAREA_PROGRAMADA");
		}
		LOGGER.info("[END] actualizarPeridos " + LocalDate.now());
	}

	@Override
	public void consolidarMetasAnuales() {
		LOGGER.info("[BEGIN] consolidarMetasAnuales " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaCorte = parametrosConstants.getFechaCorteMeta(fechaActual.getYear());
		if(fechaActual.isAfter(fechaCorte)) {
			List<Empleado> lstEmpleado = empleadoService.listar();
			lstEmpleado.forEach(e -> {
				vacacionMetaService.consolidarMetaAnual(e, fechaCorte.getYear() + 1, "TAREA_PROGRAMADA");
			});
		}
		LOGGER.info("[END] consolidarMetasAnuales " + LocalDate.now());
	}



}
