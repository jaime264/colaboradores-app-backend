package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.confianza.colaboradores.gcontenidos.server.dao.Nivel3Dao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel3;

@Service
public class Nivel3ServiceImpl implements Nivel3Service{
	
	@Autowired
	private Nivel3Dao _nivel3Dao;
	
	private static Logger logger = LoggerFactory.getLogger(Nivel3ServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<Nivel3> findByNivel2(Long idNivel2) {
		logger.info("Nivel3ServiceImpl");
		List<Nivel3> lstNivel3 = null;
		try {
			lstNivel3 = _nivel3Dao.findByNivel2(idNivel2);
		logger.info("size lstNivel3: " + lstNivel3.size());
		}catch(Exception e) {
			logger.error("Error al obtener nivel3: " + e.getMessage());
			lstNivel3 = null;
 		}
		return lstNivel3;
	}

}
