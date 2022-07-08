package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ImagenDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.OcultarComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PublicacionAppDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VideoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.OcultarComentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Video;

@Service
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	ComentarioDao comentarioDao;

	@Autowired
	ImagenDao imagenDao;

	@Autowired
	VideoDao videoDao;
	
	@Autowired
	PublicacionAppDao publicacionAppDao;
	
	@Autowired
	OcultarComentarioDao ocultarComentarioDao;

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
			com.setDescripcion(comentario.getDescripcion());
			com.setFechaCrea(LocalDateTime.now());
			com.setFlagAprobacion(true);
			com.setUsuarioBt(comentario.getUsuarioBt());
			com.setUsuarioCrea(comentario.getUsuarioCrea());
			
			Optional<Publicacion> pb  = publicacionAppDao.findById(comentario.getPublicacionId());
			
			com.setPublicacion(pb.get());
			
			Comentario cm = comentarioDao.save(com);

			if (!CollectionUtils.isEmpty(comentario.getImagenes())){
				comentario.getImagenes().forEach(e -> {
					Imagen imagen = new Imagen();
					imagen.setComentario(cm);
					imagen.setFechaCrea(LocalDateTime.now());
					imagen.setUrl(e.getUrl());
					imagen.setUsuarioCrea(comentario.getUsuarioCrea());
					imagenDao.save(imagen);
				});
			}
			if (CollectionUtils.isEmpty(comentario.getVideos())) {
				comentario.getVideos().forEach(e -> {
					Video video = new Video();
					video.setComentario(cm);
					video.setFechaCrea(LocalDateTime.now());
					video.setUrl(e.getUrl());
					video.setUsuarioCrea(comentario.getUsuarioCrea());
					videoDao.save(video);
				});
			}

			status.setCodeStatus(200);
			status.setMsgStatus("Comentario agregado");
		} catch (Exception e2) {
			status.setCodeStatus(500);
			status.setMsgStatus(e2.getMessage());
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
				cm.get().setFechaModifica(LocalDateTime.now());
				cm.get().setFlagAprobacion(comentario.getFlagAprobacion());
				cm.get().setPublicacion(comentario.getPublicacion());
				cm.get().setUsuarioModifica(comentario.getUsuarioModifica());

				comentarioDao.save(cm.get());

				if (comentario.getImagenes() != null) {
					comentario.getImagenes().forEach(e -> {
						Imagen imagen = new Imagen();
						imagen.setComentario(comentario);
						imagen.setFechaCrea(LocalDateTime.now());
						imagen.setUrl(e.getUrl());
						imagen.setUsuarioCrea(comentario.getUsuarioCrea());
						imagenDao.save(imagen);
					});
				}
				if (comentario.getVideos() != null) {
					comentario.getVideos().forEach(e -> {
						Video video = new Video();
						video.setComentario(comentario);
						video.setFechaCrea(LocalDateTime.now());
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

		Optional<Comentario> cm = comentarioDao.findById(idComentario);
		try {
			if (cm.isPresent()) {
				cm.get().setActivo(false);
				comentarioDao.save(cm.get());
			}
			status.setCodeStatus(200);
			status.setMsgStatus("Comentario Eliminado");
		} catch (Exception e) {
			status.setCodeStatus(500);
			status.setMsgStatus(e.getMessage());
		}
		
		return status;

	}

	@Override
	public List<Comentario> listByActivo() {
		
		List<Comentario> listComentarios = comentarioDao.listByActivo(true);
		
		listComentarios.stream().forEach(c -> {
			c.setPublicacionId(c.getPublicacion().getId());
		});
		
		
		return comentarioDao.listByActivo(true);
		// return null;
	}

	@Override
	public ResponseStatus updateOcultarComentario(OcultarComentario ocultarComentario) {
		// TODO Auto-generated method stub
		
		ResponseStatus status = new ResponseStatus();

		Optional<Comentario> com = comentarioDao.findById(ocultarComentario.getComentarioId());

		if (com.isPresent()) {

			if (com.get().getActivo()) {

				OcultarComentario oc = new OcultarComentario();
				oc.setComentario(com.get());
				oc.setFechaCrea(LocalDateTime.now());
				oc.setIdUsuario(ocultarComentario.getIdUsuario());
				oc.setUsuarioCrea(ocultarComentario.getIdUsuario());

				ocultarComentarioDao.save(oc);

				status.setCodeStatus(200);
				status.setMsgStatus("Comentario actualizad");
			} else {
				status.setCodeStatus(200);
				status.setMsgStatus("No existe el comentario");
			}

		} else {
			status.setCodeStatus(200);
			status.setMsgStatus("No existe la comentario");
		}

		return status;
	}

	@Override
	public ResponseStatus deleteOcultarComentario(Long idOcultarComentario) {
		ResponseStatus status = new ResponseStatus();

		try {
			ocultarComentarioDao.deleteById(idOcultarComentario);
			status.setCodeStatus(200);
			status.setMsgStatus("Ocultar comentario eliminado");
		} catch (Exception e) {
			status.setCodeStatus(500);
			status.setMsgStatus(e.getMessage());
		}
		// TODO Auto-generated method stub
		return status;
	}

}
