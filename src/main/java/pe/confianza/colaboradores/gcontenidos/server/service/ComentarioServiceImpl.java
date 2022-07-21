package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Convert;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ImagenDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.OcultarComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PublicacionAppDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VideoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.OcultarComentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Video;
import pe.confianza.colaboradores.gcontenidos.server.util.FuncionalidadApp;
import pe.confianza.colaboradores.gcontenidos.server.util.PerfilApp;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoNotificacion;

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
	
	@Autowired
	EmpleadoService empleadoService;
	
	@Autowired
	private NotificacionService notificacionService;

	@Autowired
	PublicacionService publicacionService;
	
	@Override
	public List<Comentario> list() {
		return comentarioDao.findAll();
	}

	@Override
	public List<Comentario> listByIdPublicacion(Long idPublicacion) {
		
		List<Comentario> listComentarios = comentarioDao.findByPublicacion(idPublicacion, true);
		
		listComentarios.stream().forEach(c -> {
			Empleado emp = empleadoService.buscarPorUsuarioBT(c.getUsuarioBt());
			c.setPublicacionId(c.getPublicacion().getId());
			c.setNombre(emp.getNombreCompleto());
			c.setSexo(emp.getSexo());
		});
		
		return listComentarios;
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

			status.setCodeStatus(200);
			status.setMsgStatus("Comentario agregado");
			
			try {
				
				List<Map<Integer, String>> empleadosAprobadores = publicacionAppDao.listPublicacionPefilAprobador(
						FuncionalidadApp.PUBLICACIONES.codigo, PerfilApp.EMPLEADO_APROBADOR_PUBLICACIONES.codigo);
				Optional<NotificacionTipo> tipoNot = notificacionService
						.obtenerTipoNotificacion(TipoNotificacion.COMENTARIOS_GESTION.valor);
				
				Empleado empleado = empleadoService.buscarPorUsuarioBT(com.getUsuarioBt());
				String extraData = Long.toString(cm.getId());
				List<Empleado> empleados = new ArrayList<>();
				
				empleadosAprobadores.stream().forEach(e ->{
					Empleado emp = empleadoService.buscarPorUsuarioBT(e.get("usuariobt"));
					empleados.add(emp);
				});
				
				empleados.stream().forEach(e -> {
					Notificacion notificacion = notificacionService.registrar("Aprobación de comentario",
							"el colaborador " + empleado.getNombreCompleto() + " a comentado", extraData, tipoNot.get(), e,
							cm.getUsuarioBt());

					notificacionService.enviarNotificacionApp(notificacion);
					notificacionService.enviarNotificacionCorreo(notificacion);
				});
				
				Optional<NotificacionTipo> tipoNotPub = notificacionService
						.obtenerTipoNotificacion(TipoNotificacion.COMENTARIOS.valor);
				
				Empleado empleadoPublicacion = empleadoService.buscarPorUsuarioBT(com.getPublicacion().getUsuarioBt());
				Notificacion notificacion = notificacionService.registrar("Comentario agregado",
						"el colaborador " + empleado.getNombreCompleto() + " a comentado", extraData, tipoNotPub.get(), empleadoPublicacion,
						cm.getUsuarioBt());

				notificacionService.enviarNotificacionApp(notificacion);
				notificacionService.enviarNotificacionCorreo(notificacion);

			} catch (Exception e2) {
				status.setCodeStatus(200);
				status.setMsgStatus("Comentario agregado sin notificacion");
			}
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
			Empleado emp = empleadoService.buscarPorUsuarioBT(c.getUsuarioBt());
			c.setPublicacionId(c.getPublicacion().getId());
			c.setNombre(emp.getNombreCompleto());
			c.setSexo(emp.getSexo());
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

	@Override
	public Comentario getComentario(Long id) {
		Optional<Comentario> cm = comentarioDao.findById(id);
		Empleado emp = empleadoService.buscarPorUsuarioBT(cm.get().getUsuarioBt());
		cm.get().setPublicacionId(cm.get().getPublicacion().getId());
		cm.get().setNombre(emp.getNombreCompleto());
		cm.get().setSexo(emp.getSexo());
		
		return cm.get();
	}

}
