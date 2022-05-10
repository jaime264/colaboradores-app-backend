package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Comentario;

@Service
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	ComentarioDao comentarioDao;

	@Override
	public List<Comentario> list() {
		return comentarioDao.findAll();
	}

	@Override
	public List<Comentario> listByIdPublicacion(Long idPublicacion) {
		return comentarioDao.findByPublicacion(idPublicacion);
	}

	@Override
	public ResponseStatus add(Comentario comentario) {
		ResponseStatus status = new ResponseStatus();
		comentarioDao.save(comentario);

		return status;
	}

	@Override
	public ResponseStatus update(Comentario comentario) {
		ResponseStatus status = new ResponseStatus();

		Optional<Comentario> com = comentarioDao.findById(comentario.getId());
		if (com.isPresent()) {
			comentarioDao.save(com.get());
		}

		return status;
	}

	@Override
	public ResponseStatus delete(Long idComentario) {
		ResponseStatus status = new ResponseStatus();
		comentarioDao.deleteById(idComentario);

		return status;
	}

}
