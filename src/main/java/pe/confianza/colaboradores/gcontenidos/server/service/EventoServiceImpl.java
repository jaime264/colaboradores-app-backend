package pe.confianza.colaboradores.gcontenidos.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao.EventoDao;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Evento;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

	@Autowired 
	private EventoDao eventoDao;

	@Override
	public List<Evento> listEventos() {
		return eventoDao.findAll();
	}

}
