package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.dao.EventoDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Evento;

@Service
public class EventoServiceImpl implements EventoService {

	@Autowired 
	private EventoDao eventoDao;

	@Override
	public List<Evento> listEventos() {
		return eventoDao.findAll();
	}

}
