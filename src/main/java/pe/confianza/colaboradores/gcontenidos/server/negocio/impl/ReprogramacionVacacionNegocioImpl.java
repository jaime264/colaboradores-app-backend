package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConsultaVacacionesReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ReprogramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class ReprogramacionVacacionNegocioImpl implements ReprogramacionVacacionNegocio {

	private static Logger logger = LoggerFactory.getLogger(ReprogramacionVacacionNegocioImpl.class);
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;
	
	@Autowired
	private CargaParametros cargaParametros;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public List<ResponseProgramacionVacacionReprogramar> programacionAnual(RequestConsultaVacacionesReprogramar request) {
		logger.info("[BEGIN] programacionAnuales");
		try {
			List<ResponseProgramacionVacacionReprogramar> programaciones = vacacionProgramacionService.listarProgramacionesPorAnio(cargaParametros.getAnioPresente(), request.getUsuarioBT())
					.stream().map(p -> {
						ResponseProgramacionVacacionReprogramar prog = VacacionProgramacionMapper.convertReprogramacion(p);
						if(prog.getFechaFin().getMonthValue() == LocalDate.now().getMonthValue() && prog.getIdEstado() == EstadoVacacion.APROBADO.id) {
							prog.setReprogramar(true);
						} else {
							prog.setReprogramar(false);
						}
						return prog;
					}).collect(Collectors.toList());		
			logger.info("[END] programacionAnuales");
			return programaciones;
		} catch (AppException e) {
			logger.error("[ERROR] programacionAnual", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] programacionAnual", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
		
	}

}
