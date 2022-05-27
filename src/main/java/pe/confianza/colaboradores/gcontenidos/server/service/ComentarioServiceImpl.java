package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ImagenDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VideoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Video;

@Service
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	ComentarioDao comentarioDao;

	@Autowired
	ImagenDao imagenDao;

	@Autowired
	VideoDao videoDao;

	@Override
	public List<Comentario> list() {
		return comentarioDao.findAll();
	}

	@Override
	public List<Comentario> listByIdPublicacion(Long idPublicacion) {
		return comentarioDao.findByPublicacion(idPublicacion, true);
	}

	@Override
	public ResponseStatus add(Comentario comentario) {

		ResponseStatus status = new ResponseStatus();

		try {
			Comentario com = new Comentario();
			
			com.setActivo(true);
			com.setDescripcion(null);
			com.setFecha(null);
			com.setFechaCrea(null);
			com.setFechaFin(null);
			com.setFechaInicio(null);
			com.setFlAprobacion(null);
			com.setFlgreaccion(null);
			com.setIdUsuario(null);
			com.setPublicacion(null);
			com.setReacciones(null);
			com.setUsuarioCrea(null);
			
			comentarioDao.save(comentario);

			if (!comentario.getImagenes().isEmpty()) {
				comentario.getImagenes().forEach(e -> {
					Imagen imagen = new Imagen();
					imagen.setComentario(cm);
					imagen.setFechaCrea(LocalDate.now());
					imagen.setUrl(e.getUrl());
					imagen.setUsuarioCrea(comentario.getUsuarioCrea());
					imagenDao.save(imagen);
				});
			}
			if (!comentario.getVideos().isEmpty()) {
				comentario.getVideos().forEach(e -> {
					Video video = new Video();
					video.setComentario(cm);
					video.setFechaCrea(LocalDate.now());
					video.setUrl(e.getUrl());
					video.setUsuarioCrea(comentario.getUsuarioCrea());
					videoDao.save(video);
				});
			}

			status.setCodeStatus(200);
		} catch (Exception e2) {
			status.setCodeStatus(500);
		}

		return status;
	}

	@Override
	public ResponseStatus update(Comentario comentario) {
		ResponseStatus status = new ResponseStatus();

		Optional<Comentario> cm = comentarioDao.findById(comentario.getId());
		if (cm.isPresent()) {
			if (cm.get().getActivo()) {

				cm.get().setDescripcion(comentario.getDescripcion());
				cm.get().setFechaFin(comentario.getFechaFin());
				cm.get().setFechaModifica(LocalDate.now());
				cm.get().setFlAprobacion(comentario.getFlAprobacion());
				cm.get().setFlgreaccion(comentario.getFlgreaccion());
				cm.get().setPublicacion(comentario.getPublicacion());
				cm.get().setReacciones(comentario.getReacciones());
				cm.get().setUsuarioModifica(comentario.getUsuarioModifica());

				comentarioDao.save(cm.get());

				if (comentario.getImagenes() != null) {
					comentario.getImagenes().forEach(e -> {
						Imagen imagen = new Imagen();
						imagen.setComentario(comentario);
						imagen.setFechaCrea(LocalDate.now());
						imagen.setUrl(e.getUrl());
						imagen.setUsuarioCrea(comentario.getUsuarioCrea());
						imagenDao.save(imagen);
					});
				}
				if (comentario.getVideos() != null) {
					comentario.getVideos().forEach(e -> {
						Video video = new Video();
						video.setComentario(comentario);
						video.setFechaCrea(LocalDate.now());
						video.setUrl(e.getUrl());
						video.setUsuarioCrea(comentario.getUsuarioCrea());
						videoDao.save(video);
					});
				}
				status.setCodeStatus(200);
				status.setMsgStatus("Comentario actualizado");
			} else {
				status.setCodeStatus(200);
				status.setMsgStatus("No existe comentario");
			}
		} else {
			status.setCodeStatus(200);
			status.setMsgStatus("No existe comentario");
		}

		return status;
	}

	@Override
	public ResponseStatus delete(Long idComentario) {
		ResponseStatus status = new ResponseStatus();
		comentarioDao.deleteById(idComentario);

		Optional<Comentario> cm = comentarioDao.findById(idComentario);

		if (cm.isPresent()) {
			cm.get().setActivo(false);
			comentarioDao.save(cm.get());
		}

		status.setCodeStatus(200);

		return status;

	}

	@Override
	public List<Comentario> listByActivo() {
		return comentarioDao.listByActivo(true);
		// return null;
	}

}
