package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.dao.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

@Service
public class VacacionProgramacionServiceImpl implements VacacionProgramacionService {
	
	@Autowired
	private VacacionProgramacionDao vacacionProgramacionDao;

	@Override
	public VacacionProgramacion registroSolicitud(RequestProgramacionVacacion programacion) {
		if(programacion.getFechaInicio().after(programacion.getFechaFin())) 
			throw new AppException("La fecha de inicio no puede ser mayor a la fecha fin");
		VacacionProgramacion vacacionProgramacion = new VacacionProgramacion();
		vacacionProgramacion.setId(Instant.now().toEpochMilli());
		vacacionProgramacion.setCodigoSpring(programacion.getCodigoSpring());
		vacacionProgramacion.setUsuarioBT(programacion.getUsuarioBT());
		vacacionProgramacion.setFechaInicio(programacion.getFechaInicio());
		vacacionProgramacion.setFechaFin(programacion.getFechaFin());
		vacacionProgramacion.setEstado(EstadoVacacion.REGISTRADO);
		return vacacionProgramacionDao.save(vacacionProgramacion);
		
	
	}

}
