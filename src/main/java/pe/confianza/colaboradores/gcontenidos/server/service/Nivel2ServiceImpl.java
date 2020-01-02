package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.confianza.colaboradores.gcontenidos.server.dao.Nivel2Dao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;

@Service
public class Nivel2ServiceImpl implements Nivel2Service{
	
	@Autowired
	private Nivel2Dao _nivel2Dao;

	@Override
	@Transactional(readOnly = true)
	public List<Nivel2> findByNivel1(Long idNivel1) {
		return _nivel2Dao.findByNivel1(idNivel1);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Nivel2> findById(Long id) {		
		return _nivel2Dao.findByIdNivel2(id);
	}

}
