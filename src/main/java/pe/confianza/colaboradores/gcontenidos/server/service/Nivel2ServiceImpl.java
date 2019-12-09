package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.confianza.colaboradores.gcontenidos.server.dao.Nivel2Dao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;

@Service
public class Nivel2ServiceImpl implements Nivel2Service{
	
	@Autowired
	private Nivel2Dao _nivel2Dao;

	@Override
	@Transactional(readOnly = true)
	public List<Nivel2> findByNivel1(Nivel1 nivel1) {
		return _nivel2Dao.findByNivel1(nivel1);
	}

	@Override
	@Transactional(readOnly = true)
	public Nivel2 findById(Long id) {		
		return _nivel2Dao.findById(id).get();
	}

}
