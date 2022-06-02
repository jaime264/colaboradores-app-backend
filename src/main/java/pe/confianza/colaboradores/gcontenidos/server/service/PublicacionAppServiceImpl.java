package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ImagenDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PublicacionAppDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ReaccionPubDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VideoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Reaccion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Video;

@Service
public class PublicacionAppServiceImpl implements PublicacionAppService {

	@Autowired
	PublicacionAppDao publicacionAppDao;

	@Autowired
	ImagenDao imagenDao;

	@Autowired
	VideoDao videoDao;

	@Autowired
	ComentarioDao comentarioDao;

	@Autowired
	ReaccionPubDao reaccionDao;

	@Autowired
	EmpleadoDao empleadoDao;

	@Override
	public List<Publicacion> list() {
		return publicacionAppDao.findAll();

	}

	@Override
	public ResponseStatus add(Publicacion publicacion) {

		ResponseStatus status = new ResponseStatus();
		try {

			publicacion.setFecha(LocalDateTime.now());
			publicacion.setFechaCrea(LocalDateTime.now());
			publicacion.setActivo(true);
			publicacion.setUsuarioCrea(publicacion.getIdUsuario().toString());

			Publicacion pub = publicacionAppDao.save(publicacion);

			if (pub.getImagenes() != null) {
				pub.getImagenes().forEach(e -> {
					Imagen imagen = new Imagen();
					imagen.setPublicacion(pub);
					imagen.setFechaCrea(LocalDateTime.now());
					imagen.setUrl(e.getUrl());
					imagen.setActivo(true);
					imagen.setUsuarioCrea(pub.getUsuarioCrea());
					imagenDao.save(imagen);
				});
			}
			if (pub.getVideos() != null) {
				pub.getVideos().forEach(e -> {
					Video video = new Video();
					video.setPublicacion(pub);
					video.setFechaCrea(LocalDateTime.now());
					video.setUrl(e.getUrl());
					video.setActivo(true);
					video.setUsuarioCrea(pub.getUsuarioCrea());
					videoDao.save(video);
				});
			}

			status.setCodeStatus(200);
		} catch (Exception e) {
			status.setCodeStatus(500);
		}

		return status;
	}

	@Override
	public ResponseStatus update(Publicacion publicacion) {

		ResponseStatus status = new ResponseStatus();

		Optional<Publicacion> pub = publicacionAppDao.findById(publicacion.getId());
		if (pub.isPresent()) {
			if (pub.get().getActivo()) {

				pub.get().setCategoria(publicacion.getCategoria());
				pub.get().setDescripcion(publicacion.getDescripcion());
				pub.get().setFechaFin(publicacion.getFechaFin());
				pub.get().setFechaInicio(publicacion.getFechaInicio());
				pub.get().setFechaModifica(LocalDateTime.now());
				pub.get().setFlagReacion(publicacion.getFlagReacion());
				pub.get().setFlagAprobacion(publicacion.getFlagAprobacion());
				pub.get().setMenu(publicacion.getMenu());
				pub.get().setReacciones(publicacion.getReacciones());
				pub.get().setObservacion(publicacion.getObservacion());
				pub.get().setUsuarioModifica(publicacion.getUsuarioModifica());

				guardarImg(publicacion, pub);
				guardarVid(publicacion, pub);

				publicacionAppDao.save(pub.get());
				status.setCodeStatus(200);
				status.setMsgStatus("Publicación actualizada");
			} else {
				status.setCodeStatus(200);
				status.setMsgStatus("No existe la publicación");
			}
		} else {
			status.setCodeStatus(200);
			status.setMsgStatus("No existe la publicación");
		}

		return status;
	}

	@Override
	public ResponseStatus delete(Long idPublicacion) {
		// TODO Auto-generated method stub
		ResponseStatus status = new ResponseStatus();

		Optional<Publicacion> pub = publicacionAppDao.findById(idPublicacion);

		if (pub.isPresent()) {
			pub.get().setActivo(false);
			publicacionAppDao.save(pub.get());
		}

		status.setCodeStatus(200);

		return status;
	}

	@Override
	public Publicacion publicacionById(Long id) {
		// TODO Auto-generated method stub
		Optional<Publicacion> pub = publicacionAppDao.findById(id);

		if (pub.isPresent()) {

			if (pub.get().getActivo()) {
				return pub.get();
			}
			return null;
		}

		return null;
	}

	@Override
	public List<Publicacion> listByActivo() {

		List<Publicacion> listP = publicacionAppDao.listByActivo(true);

		for (Publicacion p : listP) {

			Optional<Empleado> emp = empleadoDao.findById(p.getIdUsuario());

			p.setNombre(emp.get().getNombres() + " " + emp.get().getApellidoPaterno() + " "
					+ emp.get().getApellidoMaterno());
			p.setSexo(emp.get().getSexo());

			for (Comentario c : p.getComentarios()) {
				Optional<Empleado> cm = empleadoDao.findById(c.getIdUsuario());
				c.setNombre(emp.get().getNombres() + " " + emp.get().getApellidoPaterno() + " "
						+ emp.get().getApellidoMaterno());
				c.setSexo(emp.get().getSexo());
			}

		}

		return listP;
	}

	@Override
	public ResponseStatus updateAprobacion(Publicacion publicacion) {
		ResponseStatus status = new ResponseStatus();

		Optional<Publicacion> pub = publicacionAppDao.findById(publicacion.getId());

		if (pub.isPresent()) {

			if (pub.get().getActivo()) {

				pub.get().setCategoria(publicacion.getCategoria());
				pub.get().setMenu(publicacion.getMenu());
				pub.get().setSubmenu(publicacion.getSubmenu());
				pub.get().setFechaModifica(LocalDateTime.now());
				pub.get().setFlagReacion(publicacion.getFlagReacion());
				pub.get().setFlagAprobacion(publicacion.getFlagAprobacion());
				pub.get().setReacciones(publicacion.getReacciones());
				pub.get().setObservacion(publicacion.getObservacion());
				pub.get().setFlagPermanente(publicacion.getFlagPermanente());
				pub.get().setUsuarioModifica(publicacion.getUsuarioModifica());

				publicacionAppDao.save(pub.get());
				status.setCodeStatus(200);
				status.setMsgStatus("Publicación actualizada");
			} else {
				status.setCodeStatus(200);
				status.setMsgStatus("No existe la publicación");
			}

		} else {
			status.setCodeStatus(200);
			status.setMsgStatus("No existe la publicación");
		}

		return null;
	}

	@Override
	public ResponseStatus updateReaccion(Reaccion reaccion) {
		ResponseStatus status = new ResponseStatus();

		Optional<Publicacion> pub = publicacionAppDao.findById(reaccion.getPublicacionId());

		if (pub.isPresent()) {

			if (pub.get().getActivo()) {

				Reaccion reac = new Reaccion();
				reac.setPublicacion(pub.get());
				reac.setFechaCrea(LocalDate.now());
				reac.setIdUsuario(reaccion.getIdUsuario());
				reac.setUsuarioCrea(reaccion.getIdUsuario());

				reaccionDao.save(reac);

				status.setCodeStatus(200);
				status.setMsgStatus("Publicación actualizada");
			} else {
				status.setCodeStatus(200);
				status.setMsgStatus("No existe la publicación");
			}

		} else {
			status.setCodeStatus(200);
			status.setMsgStatus("No existe la publicación");
		}

		return status;
	}

	public void guardarImg(Publicacion publicacion, Optional<Publicacion> pub) {

		if (!publicacion.getImagenes().isEmpty()) {

			if (!pub.get().getImagenes().isEmpty()) {
				for (Imagen img : pub.get().getImagenes()) {
					Optional<Imagen> ig = imagenDao.findById(img.getId());
					ig.get().setActivo(false);
					imagenDao.save(ig.get());
				}
			}

			for (Imagen img : publicacion.getImagenes()) {

				if (img.getId() == null) {
					Imagen imagen = new Imagen();
					imagen.setPublicacion(publicacion);
					imagen.setFechaCrea(LocalDateTime.now());
					imagen.setUrl(img.getUrl());
					imagen.setActivo(true);
					imagen.setUsuarioCrea(publicacion.getUsuarioModifica());
					imagenDao.save(imagen);
				} else {
					Optional<Imagen> ig = imagenDao.findById(img.getId());

					if (ig.isPresent()) {
						ig.get().setPublicacion(publicacion);
						ig.get().setFechaModifica(LocalDateTime.now());
						ig.get().setUsuarioModifica(publicacion.getUsuarioModifica());
						ig.get().setUrl(img.getUrl());
						ig.get().setActivo(true);
						imagenDao.save(ig.get());
					}
				}

			}
		} else {
			if (!pub.get().getImagenes().isEmpty()) {
				for (Imagen img : pub.get().getImagenes()) {
					Optional<Imagen> ig = imagenDao.findById(img.getId());
					ig.get().setActivo(false);
					imagenDao.save(ig.get());
				}
			}
		}

	}

	public void guardarVid(Publicacion publicacion, Optional<Publicacion> pub) {

		if (!publicacion.getVideos().isEmpty()) {

			if (!pub.get().getVideos().isEmpty()) {
				for (Video video : pub.get().getVideos()) {
					Optional<Video> vd = videoDao.findById(video.getId());
					vd.get().setActivo(false);
					videoDao.save(vd.get());
				}
			}

			for (Video vdo : publicacion.getVideos()) {

				if (vdo.getId() == null) {
					Video video = new Video();
					video.setPublicacion(publicacion);
					video.setFechaCrea(LocalDateTime.now());
					video.setUrl(vdo.getUrl());
					video.setActivo(true);
					video.setUsuarioCrea(publicacion.getUsuarioModifica());
					videoDao.save(video);
				} else {
					Optional<Video> vo = videoDao.findById(vdo.getId());

					if (vo.isPresent()) {
						vo.get().setPublicacion(publicacion);
						vo.get().setFechaModifica(LocalDateTime.now());
						vo.get().setUsuarioModifica(publicacion.getUsuarioModifica());
						vo.get().setUrl(vdo.getUrl());
						vo.get().setActivo(true);
						videoDao.save(vo.get());
					}
				}

			}
		} else {
			if (!pub.get().getVideos().isEmpty()) {
				for (Video vdo : pub.get().getVideos()) {
					Optional<Video> vo = videoDao.findById(vdo.getId());
					vo.get().setActivo(false);
					videoDao.save(vo.get());
				}
			}
		}
	}

}
