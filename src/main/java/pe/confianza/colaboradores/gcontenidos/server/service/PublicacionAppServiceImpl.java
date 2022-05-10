package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.PublicacionAppDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Publicacion;

@Service
public class PublicacionAppServiceImpl implements PublicacionAppService{

	
	@Autowired
	PublicacionAppDao publicacionAppDao;
	
	
	@Override
	public List<Publicacion> list() {
		// TODO Auto-generated method stub
		return publicacionAppDao.findAll();

	}

	@Override
	public ResponseStatus add(Publicacion publicacion) {
		// TODO Auto-generated method stub
		ResponseStatus status = new ResponseStatus();
		status.setResultObj(publicacionAppDao.save(publicacion));
		status.setCodeStatus(200);
		return status;
	}

	@Override
	public ResponseStatus update(Publicacion publicacion) {
		// TODO Auto-generated method stub
		ResponseStatus status = new ResponseStatus();
		
		Optional<Publicacion> com = publicacionAppDao.findById(publicacion.getId());
		if (com.isPresent()) {
			status.setResultObj(publicacionAppDao.save(com.get()));
			status.setCodeStatus(200);
		}
		
		status.setCodeStatus(401);

		return status;
	}

	@Override
	public ResponseStatus delete(Long idPublicacion) {
		// TODO Auto-generated method stub
		ResponseStatus status = new ResponseStatus();
		
		publicacionAppDao.deleteById(idPublicacion);
		status.setCodeStatus(200);

		return status;
	}


	

}
