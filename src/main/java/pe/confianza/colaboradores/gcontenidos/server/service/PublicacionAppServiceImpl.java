package pe.confianza.colaboradores.gcontenidos.server.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.confianza.colaboradores.gcontenidos.server.api.spring.EmpleadoApi;
import pe.confianza.colaboradores.gcontenidos.server.bean.NotificacionPublicacionDataExtra;
import pe.confianza.colaboradores.gcontenidos.server.bean.NotificacionPublicacionMediaDataExtra;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestPublicacionGestorContenido;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.PublicacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ImagenDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PublicacionAppDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ReaccionPubDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.UsuarioPublicacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VideoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Reaccion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UsuarioPublicacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Video;
import pe.confianza.colaboradores.gcontenidos.server.negocio.EnvioNotificacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoNotificacion;

@Service
public class PublicacionAppServiceImpl implements PublicacionAppService {
	
	private static Logger logger = LoggerFactory.getLogger(PublicacionAppServiceImpl.class);

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

	@Autowired
	EmpleadoApi empleadoApi;

	@Autowired
	EmpleadoService empleadoService;
	
	@Autowired
	private UsuarioPublicacionDao usuarioPublicacionDao;
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private EnvioNotificacionNegocio envioNotificacionNegocio;

	@Override
	public List<Publicacion> list() {
		return publicacionAppDao.findAll();

	}

	@Override
	public ResponseStatus add(Publicacion publicacion) {

		ResponseStatus status = new ResponseStatus();
		Publicacion pub = guardar(publicacion);
		if(pub != null) {
			status.setCodeStatus(200);
		} else {
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

		try {
			if (pub.isPresent()) {
				pub.get().setActivo(false);
				publicacionAppDao.save(pub.get());
			}
			status.setCodeStatus(200);
			status.setMsgStatus("Publicacion eliminada");
		} catch (Exception e) {
			status.setCodeStatus(500);
			status.setMsgStatus(e.getMessage());
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
			Empleado emp = empleadoService.buscarPorUsuarioBT(p.getUsuarioBt());

			p.setNombre(emp.getNombres() + " " + emp.getApellidoPaterno() + " " + emp.getApellidoMaterno());
			p.setSexo(emp.getSexo());

			for (Comentario c : p.getComentarios()) {
				Empleado emC = empleadoService.buscarPorUsuarioBT(c.getUsuarioBt());

				c.setNombre(emC.getNombres() + " " + emC.getApellidoPaterno() + " " + emC.getApellidoMaterno());
				c.setSexo(emC.getSexo());
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
				reac.setFechaCrea(LocalDateTime.now());
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

	@Override
	public ResponseStatus deleteReaccion(Long idReaccion) {
		ResponseStatus status = new ResponseStatus();

		try {
			reaccionDao.deleteById(idReaccion);
			status.setCodeStatus(200);
			status.setMsgStatus("reaccion eliminada");
		} catch (Exception e) {
			status.setCodeStatus(200);
			status.setMsgStatus(e.getMessage());
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

	@Transactional
	@Override
	public ResponseStatus registro(RequestPublicacionGestorContenido request) {
		logger.info("[BEGIN] registro");
		Publicacion publicacion = PublicacionMapper.convert(request);
		Publicacion pub = guardar(publicacion);
		if(pub == null)
			throw new AppException("Ocurrió un error al registrar publicación");
		final Publicacion pubActual = publicacionById(pub.getId());
		if(pubActual == null)
			throw new AppException("Ocurrió un error al registrar publicación");
		Optional<NotificacionTipo> tipoNot = notificacionService.obtenerTipoNotificacion(TipoNotificacion.PUBLICACION_APP.valor);
		if(!tipoNot.isPresent())
			throw new AppException("No se encontró el tipo de notificacion");
		try {
			Empleado empleadoPublicador = empleadoService.buscarPorUsuarioBT(pub.getUsuarioBt());
			if(empleadoPublicador != null) {
				List<Notificacion> notificaciones = new ArrayList<>();
				for (String u : request.getUsuarios()) {
					Empleado empelado = empleadoService.buscarPorUsuarioBT(u.trim());
					if(empelado.getId() != null) {
						UsuarioPublicacion usuarioPublicacion = new UsuarioPublicacion();
						usuarioPublicacion.setEmpleado(empelado);
						usuarioPublicacion.setPublicacion(pubActual);
						usuarioPublicacion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
						usuarioPublicacion.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
						usuarioPublicacion.setUsuarioCrea(pubActual.getUsuarioBt());
						usuarioPublicacion.setFechaCrea(LocalDateTime.now());
						usuarioPublicacionDao.save(usuarioPublicacion);
						
						String extradata = generarExtraDataPublicacion(pubActual, empleadoPublicador, 3);
						StringBuilder tituloSb = new StringBuilder().append(pubActual.getMenu()).append(" - ").append(pubActual.getSubmenu());
						if(pubActual.getCategoria() != null) {
							tituloSb.append(" - ").append(pub.getCategoria());
						}
						Notificacion not = notificacionService.registrar(
								tituloSb.toString(), pubActual.getDescripcion(), extradata, 
								tipoNot.get(), empelado, pubActual.getUsuarioCrea());
						notificaciones.add(not);
					}
				}
				envioNotificacionNegocio.enviarNotificacionesApp(notificaciones);
				envioNotificacionNegocio.enviarNotificacionesCorreo(notificaciones);
			}
			
		} catch (Exception e) {
			logger.error("[ERROR] registro", e);
			throw new AppException("Ocurrió un error al registrar usuario y notificación", e);
		}
		ResponseStatus response = new ResponseStatus();
		response.setCodeStatus(Constantes.COD_OK);
		response.setMsgStatus(Constantes.OK);
		response.setResultObj(pub);
		logger.info("[END] registro");
		return response;
	}
	
	private Publicacion guardar(Publicacion publicacion) {
		logger.info("[BEGIN] guardar");
		try {
			publicacion.setFecha(LocalDateTime.now());
			publicacion.setFechaCrea(LocalDateTime.now());
			publicacion.setActivo(true);
			publicacion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);

			Publicacion pub = publicacionAppDao.saveAndFlush(publicacion);

			List<Imagen> imagenes = new ArrayList<>();
			List<Video> videos = new ArrayList<>();
			if (pub.getImagenes() != null) {
				for (Imagen e : pub.getImagenes()) {
					Imagen imagen = new Imagen();
					imagen.setPublicacion(pub);
					imagen.setFechaCrea(LocalDateTime.now());
					imagen.setUrl(e.getUrl());
					imagen.setActivo(true);
					imagen.setUsuarioCrea(pub.getUsuarioCrea());
					imagen.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
					imagenes.add(imagen);
				}
			}
			
			if (pub.getVideos() != null) {
				for (Video e : pub.getVideos()) {
					Video video = new Video();
					video.setPublicacion(pub);
					video.setFechaCrea(LocalDateTime.now());
					video.setUrl(e.getUrl());
					video.setActivo(true);
					video.setUsuarioCrea(pub.getUsuarioCrea());
					video.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
					videos.add(video);
				}
			}
			imagenDao.saveAll(imagenes);
			videoDao.saveAll(videos);
			pub.setImagenes(imagenes);
			pub.setVideos(videos);
			logger.info("[END] guardar");
			return pub;
		} catch (Exception e) {
			logger.error("[ERROR] guardar", e);
			return null;
		}
	}
	
	private String generarExtraDataPublicacion(Publicacion pub, Empleado publicador, int subTipo) {
		logger.info("[BEGIN] generarExtraDataPublicacion ");		
		try {
			NotificacionPublicacionDataExtra extraData = new NotificacionPublicacionDataExtra();
			extraData.setSubTipo(subTipo);
			extraData.setIdPublicacion(pub.getId());
			extraData.setUsuarioCrea(pub.getUsuarioCrea());
			extraData.setMenu(pub.getMenu());
			extraData.setSubmenu(pub.getSubmenu());
			extraData.setCategoria(pub.getCategoria());
			extraData.setDescripcion(pub.getDescripcion());
			extraData.setObservacion(pub.getObservacion());
			extraData.setFlagAprobacion(pub.getFlagAprobacion());
			extraData.setSexo(publicador.getSexo());
			extraData.setNombre(publicador.getNombres() + " " + publicador.getApellidoPaterno());
			extraData.setGestorContenido(pub.getGestorContenido());
			List<NotificacionPublicacionMediaDataExtra> imagenes = pub.getImagenes().stream().map(i -> {
				NotificacionPublicacionMediaDataExtra media = new NotificacionPublicacionMediaDataExtra();
				media.setUsuarioCrea(i.getUsuarioCrea());
				media.setFechaCrea(null);
				media.setUsuarioModifica(i.getUsuarioModifica());
				media.setFechaModifica(null);
				media.setEstadoRegistro(i.getEstadoRegistro());
				media.setId(i.getId());
				media.setActivo(i.getActivo());
				media.setUrl(i.getUrl());
				return media;
			}).collect(Collectors.toList());
			List<NotificacionPublicacionMediaDataExtra> videos = pub.getVideos().stream().map(i -> {
				NotificacionPublicacionMediaDataExtra media = new NotificacionPublicacionMediaDataExtra();
				media.setUsuarioCrea(i.getUsuarioCrea());
				media.setFechaCrea(null);
				media.setUsuarioModifica(i.getUsuarioModifica());
				media.setFechaModifica(null);
				media.setEstadoRegistro(i.getEstadoRegistro());
				media.setId(i.getId());
				media.setActivo(i.getActivo());
				media.setUrl(i.getUrl());
				return media;
			}).collect(Collectors.toList());
			extraData.getImagenes().addAll(imagenes);
			extraData.getVideos().addAll(videos);
			ObjectMapper objectMapper = new ObjectMapper();
			DateFormat df = new SimpleDateFormat(Constantes.FORMATO_FECHA_HORA);
			objectMapper.setDateFormat(df);
			logger.info("[END] generarExtraDataPublicacion");
			return objectMapper.writeValueAsString(extraData);
		} catch (Exception e) {
			logger.error("[ERROR] " + pub.toString());
			logger.error("[ERROR] generarExtraDataPublicacion", e);
			return "";
		}
	}



}
