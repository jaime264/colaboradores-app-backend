package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.dao.ReaccionDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Reaccion;

@Service
public class ReaccionServiceImpl implements ReaccionService {
	
	@Autowired 
	private ReaccionDao reaccionDao;

	@Override
	public List<Reaccion> listReacciones() {
		return reaccionDao.findAll();
	}

}
