package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.Mail;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.PeriodoVacacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametrosConstants;
import pe.confianza.colaboradores.gcontenidos.server.util.EmailUtil;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;


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
	
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public void actualizarEstadoProgramaciones() {
		LOGGER.info("[BEGIN] actualizarEstadoProgramaciones " + LocalDate.now());
		vacacionProgramacionService.actualizarEstadoProgramaciones();
		LOGGER.info("[END] actualizarEstadoProgramaciones " + LocalDate.now());
	}

	@Override
	public void actualizarPeriodos() {
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

	@Override
	public void notificarHabilitacionRegistroProgramacion() {
		/*final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ahora = LocalDateTime.now();
		Integer horaEnvioNotificacion = parametrosConstants.getHoraEnvioNotificacionVacaciones();
		LocalDateTime horaEnvio = LocalDateTime.parse(ahora.getYear() + "-" + ahora.getMonthValue() + "-" + ahora.getDayOfMonth() + " " + horaEnvioNotificacion + ":00:00", formatter);
		LocalDateTime horaEnvioMax = horaEnvio.plusMinutes(10).plusMinutes(59);
		if(ahora.isAfter(horaEnvio) && ahora.isBefore(horaEnvioMax)) {
			List<Empleado> lstEmpleado = empleadoService.listar();
			lstEmpleado.forEach(e -> {
				if(e.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor)) {
					Mail mail = new Mail();
					mail.setAsunto("REGISTRO DE VACACIONES");
					mail.setContenido(new HashMap<>());
					mail.getContenido().put("empleado", "Hola, " + e.getNombres() + " " + e.getApellidoPaterno());
					mail.getContenido().put("mensaje", "Estimado Colaborador, a partir del día de mañana 1ro de Noviemebre debes registrar tus  vacaciones para el año XXXX, tienes un plazo máximo hasta el 30 de noviembre para culminar esta actividad");
					mail.setReceptor(e.getEmail());
					mail.setEmisor("desarrollofc@confianza.pe");
					emailUtil.enviarEmail(mail);
				}
			});
		}*/
		
	}
	
	



}
