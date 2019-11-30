package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.confianza.colaboradores.gcontenidos.server.dao.Nivel1Dao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;

@Service
public class Nivel1ServiceImpl implements Nivel1Service{
	
	@Autowired 
	private Nivel1Dao nivel1Dao;

	@Override
	@Transactional(readOnly = true)
	public List<Nivel1> findAll() {
		return nivel1Dao.findAll();
	}

}
