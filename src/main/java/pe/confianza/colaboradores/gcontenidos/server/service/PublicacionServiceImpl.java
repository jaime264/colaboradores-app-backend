package pe.confianza.colaboradores.gcontenidos.server.service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ReaccionPost;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.bean.Usuario;
import pe.confianza.colaboradores.gcontenidos.server.dao.DispositivoDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.PublicacionDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.PublicacionUsuarioDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Dispositivo;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.PublicacionUsuario;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Comentario;

@Service
public class PublicacionServiceImpl implements PublicacionService {
	
	@Autowired 
	private PublicacionDao postDao;
	
	@Autowired 
	private PublicacionUsuarioDao relationDao;
	
	@Autowired 
	private DispositivoDao dispositivoDao;
	
	@Autowired
	private ComentarioDao comentarioDao;

	@Override
	public ResponseStatus createPost(List<Publicacion> postsIn) {
		List<Publicacion> postsOut = new ArrayList<Publicacion>();
		List<Dispositivo> devices = new ArrayList<Dispositivo>();
		ResponseStatus status = new ResponseStatus();
		try {
			if (postsIn.size() > 0) {
				String msgValidar = validatePost(postsIn.get(0));
				if (msgValidar.equals("OK")) {
					postsOut = postDao.saveAll(postsIn);
					if (postsOut.size() > 0) {
						for (Publicacion publicacion : postsOut) {
							List<PublicacionUsuario> relacion = new ArrayList<PublicacionUsuario>();
							List<String> usuarios = new ArrayList<String>();
							for (Usuario usuario: publicacion.getUsuarios()) {
								PublicacionUsuario rel = new PublicacionUsuario();
								rel.setIdPublicacion(publicacion.getId());
								rel.setIdUsuario(usuario.getUsuarioBT());
								rel.setIdReaccion(0);
								rel.setIdReaccionAnterior(0);
								relacion.add(rel);
								usuarios.add(usuario.getUsuarioBT());
							}
							if (relacion.size() > 0) {
								relationDao.saveAll(relacion);
							}
							if (usuarios.size() > 0) {
								devices = dispositivoDao.findByIdDeviceUser(usuarios);
							}
						}
						status.setCodeStatus(0);
						status.setMsgStatus("Registro creado correctamente");
						status.setResultObj(devices);
					} else {
						status.setCodeStatus(99);
						status.setMsgStatus("No se pudo registrar la publicación");
					}
				} else {
					status.setCodeStatus(99);
					status.setMsgStatus(msgValidar);
				}
			} else {
				status.setCodeStatus(99);
				status.setMsgStatus("No se tienen publicaciones la publicación");
			}
		} catch(Exception ex) {
			status.setCodeStatus(99);
			status.setMsgStatus(ex.getMessage());
		}
		return status;
	}
	
	@Override
	public ResponseStatus addReaccion(ParamsReaccion paramsReaccion) {
		List<PublicacionUsuario> relationSave = new ArrayList<PublicacionUsuario>();
		ResponseStatus status = new ResponseStatus();
		try {
			PublicacionUsuario pu = relationDao.findByRelation(paramsReaccion.getIdPublicacion(), paramsReaccion.getUsuario());
			if (pu != null) {
				PublicacionUsuario savePU = new PublicacionUsuario();
				savePU.set_id(pu.get_id());
				savePU.setIdPublicacion(pu.getIdPublicacion());
				savePU.setIdReaccion(paramsReaccion.getActivo() == 1 ? paramsReaccion.getIdReaccion() : 0);
				savePU.setIdUsuario(paramsReaccion.getUsuario());
				savePU.setIdReaccionAnterior(pu.getIdReaccion());
				relationSave.add(savePU);
				List<PublicacionUsuario> relationOut = relationDao.saveAll(relationSave);
				if (relationOut.size() > 0) {
					Optional<Publicacion> resultPU = postDao.findByIdPost(pu.getIdPublicacion());
					List<Publicacion> inPublicacion = new ArrayList<Publicacion>();
					List<Publicacion> outPublicacion = new ArrayList<Publicacion>();
					if (resultPU != null && resultPU.isPresent()) {
						Publicacion publicacion = resultPU.get();
						List<ReaccionPost> lstReacciones = new ArrayList<ReaccionPost>();
						int reaccion = paramsReaccion.getIdReaccion();
						int reaccionAnterior = savePU.getIdReaccionAnterior();
						int activo = paramsReaccion.getActivo();
						for (ReaccionPost r: publicacion.getReacciones()) {
							if (r.getId() == reaccion) {
								ReaccionPost nr = new ReaccionPost();
								nr.setId(r.getId());
								nr.setNombre(r.getNombre());
								nr.setContador((activo == 1 ? r.getContador() + 1 : r.getContador() - 1));
								nr.setActivo(r.getActivo());
								lstReacciones.add(nr);
							} else if (r.getId() == reaccionAnterior){
								ReaccionPost nr = new ReaccionPost();
								nr.setId(r.getId());
								nr.setNombre(r.getNombre());
								nr.setContador(r.getContador() > 0 ? r.getContador() - 1 : 0);
								nr.setActivo(r.getActivo());
								lstReacciones.add(nr);
							} else {
								lstReacciones.add(r);
							}
						}
						publicacion.setReacciones(lstReacciones);
						inPublicacion.add(publicacion);
						outPublicacion = postDao.saveAll(inPublicacion);
						if (outPublicacion.size() > 0) {
							status.setCodeStatus(0);
							status.setMsgStatus("Reacción registrada correctamente");
							status.setResultObj(outPublicacion.get(0).getId());
						} else {
							status.setCodeStatus(99);
							status.setMsgStatus("No es posible actualizar publicación");
						}
					} else {
						status.setCodeStatus(99);
						status.setMsgStatus("No existe publicación en base de datos");
					}
					
				} else {
					status.setCodeStatus(99);
					status.setMsgStatus("No se pudo actualizar la relaciòn publicación y usuario");
				}
			} else {
				status.setCodeStatus(99);
				status.setMsgStatus("No existe relación con la publicaciòn y el usuario");
			}
		} catch(Exception ex) {
			status.setCodeStatus(99);
			status.setMsgStatus(ex.getMessage());
		}
		return status;
	}
	
	@Override
	public Optional<Publicacion> findByIdPost(Long id) {		
		return postDao.findByIdPost(id);
	}
	
	@Override
	public Optional<Publicacion> findByIdPostUser(Long id, String user) {		
		Optional<Publicacion> optional = postDao.findByIdPost(id);
		Publicacion post = optional.get();
		if (post != null) {
			// Seteamos solo el usuario de busqueda
			List<Usuario> lstUsuarios = new ArrayList<Usuario>();
			Usuario usuario = new Usuario();
			usuario.setUsuarioBT(user);
			usuario.setUltimaPublicacion(0L);
			lstUsuarios.add(usuario);
			post.setUsuarios(lstUsuarios);
			// Validamos el estado de la reacción
			List<PublicacionUsuario> relaciones = relationDao.findAllReaction(post.getId(), user);
			if (relaciones.size() > 0) {
				List<ReaccionPost> lstReacciones = new ArrayList<ReaccionPost>();
				int reaccion = relaciones.get(0).getIdReaccion();
				for (ReaccionPost r: post.getReacciones()) {
					ReaccionPost nr = new ReaccionPost();
					nr.setId(r.getId());
					nr.setNombre(r.getNombre());
					nr.setContador(r.getContador());
					nr.setActivo((r.getId() == reaccion) ? 1 : 0);
					lstReacciones.add(nr);
				}
				post.setReacciones(lstReacciones);
			}
		}
		return optional;
	}

	@Override
	public List<Publicacion> listPost() {
				
		List<Publicacion> publicaciones =  postDao.findAll();
		
		return publicaciones;

	}
	
	@Override
	public List<Publicacion> listPostUser(String user, Long lastPost, Integer size, Boolean back) {
		List<Publicacion> lstFinal = new ArrayList<Publicacion>();
		List<Publicacion> lstFind = new ArrayList<Publicacion>();
		if (!back) {
			//List<Publicacion> lstFind = postDao.findAllUser(user, lastPost);
			PageRequest request = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "fecha"));
			lstFind = postDao.findAllUser(user, lastPost, request);
		} else {
			//List<Publicacion> lstFind = postDao.findAllUser(user, lastPost);
			PageRequest request = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "fecha"));
			lstFind = postDao.findAllUserBack(user, lastPost, request);
		}
		
		
		if (lstFind.size() > 0) {
			// Seteamos solo el usuario de busqueda
			List<Usuario> lstUsuarios = new ArrayList<Usuario>();
			Usuario usuario = new Usuario();
			usuario.setUsuarioBT(user);
			usuario.setUltimaPublicacion(lastPost);
			lstUsuarios.add(usuario);
			for(Publicacion post : lstFind) {
				post.setUsuarios(lstUsuarios);
				// Validamos el estado de la reacción
				List<PublicacionUsuario> relaciones = relationDao.findAllReaction(post.getId(), user);
				if (relaciones.size() > 0) {
					List<ReaccionPost> lstReacciones = new ArrayList<ReaccionPost>();
					int reaccion = relaciones.get(0).getIdReaccion();
					for (ReaccionPost r: post.getReacciones()) {
						ReaccionPost nr = new ReaccionPost();
						nr.setId(r.getId());
						nr.setNombre(r.getNombre());
						nr.setContador(r.getContador());
						nr.setActivo((r.getId() == reaccion) ? 1 : 0);
						lstReacciones.add(nr);
					}
					post.setReacciones(lstReacciones);
				}
				lstFinal.add(post);
			}
		}
		return lstFinal;
	}				
	
	private String validatePost(Publicacion post) {
		String validado = null;
		if (post.getTitulo() == null || post.getTitulo().equals("")) {
			validado = "Debe ingresar un titulo para la publicación";
		} else if (post.getFlgmultimedia() == 1 && post.getImagenes().size() == 0) {
			validado = "Debe ingresar al menos una imagen";
		} else if (post.getFlgmultimedia() == 2 && post.getVideos().size() == 0) {
			validado = "Debe ingresar una url válida para el video";
		} else if (post.getIdnivel1() == null || post.getIdnivel1() <= 0) {
			validado = "Debe seleccionar el Nivel 1";
		} else if (post.getIdnivel2() == null || post.getIdnivel2() <= 0) {
			validado = "Debe seleccionar el Nivel 2";
		} else if (post.getFecha() == null || post.getFecha() <= 0) {
			validado = "Debe seleccionar una fecha válida";
		} else {
			validado = "OK";
		}
		return validado;
	}

}
