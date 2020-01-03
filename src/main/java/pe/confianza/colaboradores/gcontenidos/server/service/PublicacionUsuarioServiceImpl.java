package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.dao.PublicacionUsuarioDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.PublicacionUsuario;

@Service
public class PublicacionUsuarioServiceImpl implements PublicacionUsuarioService {
	
	@Autowired 
	private PublicacionUsuarioDao relacionDao;
	
	@Override
	public void createRelation(List<PublicacionUsuario> relacion) {
		relacionDao.saveAll(relacion);
	}

	@Override
	public List<PublicacionUsuario> findAllReaction(Long idPublicacion,
			String idUsuario) {
		return relacionDao.findAllReaction(idPublicacion, idUsuario);
	}


}
