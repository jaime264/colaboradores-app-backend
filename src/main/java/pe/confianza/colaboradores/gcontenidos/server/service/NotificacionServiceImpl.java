package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pe.confianza.colaboradores.gcontenidos.server.bean.Mail;
import pe.confianza.colaboradores.gcontenidos.server.bean.MailFile;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFirebaseMessaging;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFirebaseMessagingData;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.NotificacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.NotificacionTipoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao.DispositivoDao;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Dispositivo;
import pe.confianza.colaboradores.gcontenidos.server.util.EmailUtil;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.FirebaseCloudMessagingClient;

@Service
public class NotificacionServiceImpl implements NotificacionService {
	
	private static Logger logger = LoggerFactory.getLogger(NotificacionServiceImpl.class);

	@Autowired
	private NotificacionTipoDao notificacionTipoDao;
	
	@Autowired
	private NotificacionDao notificacionDao;
	
	@Autowired
	private DispositivoDao dispositivoDao;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Override
	public List<NotificacionTipo> obtenerTipos() {
		List<NotificacionTipo> lst = notificacionTipoDao.listarActivos();
		lst = lst == null ? new ArrayList<>() : lst;
		return lst;
	}

	@Override
	public Page<Notificacion> consultar(Empleado empleado, NotificacionTipo tipo, int numeroPagina, int tamanioPagina) {
		Pageable paginacion = PageRequest.of(numeroPagina, tamanioPagina);
		return notificacionDao.consultar(empleado.getId(), tipo.getId(), paginacion);
	}

	@Override
	public Optional<NotificacionTipo> obtener(String codigo) {
		return notificacionTipoDao.findOneByCodigo(codigo);
	}

	@Override
	public Optional<Notificacion> obtener(long id) {
		return notificacionDao.findById(id);
	}

	@Override
	public Notificacion actualizar(Notificacion notificacion, String usuarioActualiza) {
		notificacion.setFechaModifica(LocalDateTime.now());
		notificacion.setUsuarioModifica(usuarioActualiza);
		return notificacionDao.saveAndFlush(notificacion);
	}

	@Override
	public List<Notificacion> listarNotificacionesNoEnviadasApp() {
		List<Notificacion> notificacionesPendientes = notificacionDao.listarNotificacionesNoEnviadasApp();
		notificacionesPendientes = notificacionesPendientes == null ? new ArrayList<>() : notificacionesPendientes;
		return notificacionesPendientes;
	}

	@Override
	public List<Notificacion> listarNotificacionesNoEnviadasCorreo() {
		List<Notificacion> notificacionesPendientes = notificacionDao.listarNotificacionesNoEnviadasCorreo();
		notificacionesPendientes = notificacionesPendientes == null ? new ArrayList<>() : notificacionesPendientes;
		return notificacionesPendientes;
	}

	@Override
	public void enviarNotificacionApp(Notificacion notificacion) {
		logger.info("[BEGIN] enviarNotificacionApp");
		try {
			Optional<Dispositivo> optDispositivo = dispositivoDao.findByUser(notificacion.getEmpleado().getUsuarioBT());
			if(optDispositivo.isPresent()) {
				RequestFirebaseMessagingData data = new RequestFirebaseMessagingData();
				data.setTitle(notificacion.getTitulo());
				data.setBody(notificacion.getDescripcion());
				data.setExtra(new HashMap<>());
				data.getExtra().put("extra", notificacion.getData());
				String dispositivoWeb = optDispositivo.get().getIdDispositivoFirebase();
				String dispositivoMobile = optDispositivo.get().getIdDispositivo();
				if(StringUtils.hasText(dispositivoWeb)) {
					RequestFirebaseMessaging request = new RequestFirebaseMessaging();
					request.setTokens(Arrays.asList(new String[] {dispositivoWeb}));
					request.setData(data);
					if(FirebaseCloudMessagingClient.sendMessageToWeb(request)) {
						notificacion.setEnviadoApp(true);
						actualizar(notificacion, "APP");
					}
				}
				if(StringUtils.hasText(dispositivoMobile)) {
					RequestFirebaseMessaging request = new RequestFirebaseMessaging();
					request.setTokens(Arrays.asList(new String[] {dispositivoMobile}));
					request.setData(data);
					if(FirebaseCloudMessagingClient.sendMessageToApp(request)) {
						notificacion.setEnviadoApp(true);
						actualizar(notificacion, "APP");
					}
				}
			}
		} catch (Exception e) {
			logger.error("[ERROR] enviarNotificacionApp", e);
		}
		logger.info("[END] enviarNotificacionApp");
	}

	@Override
	public void enviarNotificacionCorreo(Notificacion notificacion) {
		logger.info("[BEGIN] enviarNotificacionCorreo");
		try {
			Mail mail = new Mail();
			mail.setAsunto(notificacion.getTitulo());
			mail.setContenido(new HashMap<>());
			mail.getContenido().put("empleado", "Hola, " + notificacion.getEmpleado().getNombres() + " " + notificacion.getEmpleado().getApellidoPaterno());
			mail.getContenido().put("mensaje", notificacion.getDescripcion());
			mail.setReceptor(notificacion.getEmpleado().getEmail().trim());
			mail.setEmisor("desarrollofc@confianza.pe");
			
			if(emailUtil.enviarEmail(mail)) {
				notificacion.setEnviadoCorreo(true);
				actualizar(notificacion, "APP");
			}
		} catch (Exception e) {
			logger.error("[ERROR] enviarNotificacionCorreo", e);
		}
		logger.info("[END] enviarNotificacionCorreo");
	}
	
	@Override
	public void enviarNotificacionCorreo(Notificacion notificacion, String[] receptorCC) {
		logger.info("[BEGIN] enviarNotificacionCorreo");
		try {
			Mail mail = new Mail();
			mail.setAsunto(notificacion.getTitulo());
			mail.setContenido(new HashMap<>());
			mail.getContenido().put("empleado", "Hola, " + notificacion.getEmpleado().getNombres() + " " + notificacion.getEmpleado().getApellidoPaterno());
			mail.getContenido().put("mensaje", notificacion.getDescripcion());
			mail.setReceptor(notificacion.getEmpleado().getEmail().trim());
			mail.setEmisor("desarrollofc@confianza.pe");
			mail.setReceptorCC(receptorCC);
			
			if(emailUtil.enviarEmail(mail)) {
				notificacion.setEnviadoCorreo(true);
				actualizar(notificacion, "APP");
			}
		} catch (Exception e) {
			logger.error("[ERROR] enviarNotificacionCorreo", e);
		}
		logger.info("[END] enviarNotificacionCorreo");
	}


	@Override
	public Notificacion registrar(String titulo, String descripcion, String extraData, NotificacionTipo tipo,
			Empleado empleado, String usuarioRegistra) {
		logger.info("[BEGIN] registrar");
		Notificacion notificacion = new Notificacion();
		notificacion.setData(extraData);
		notificacion.setDescripcion(descripcion);
		notificacion.setTitulo(titulo);
		notificacion.setTipo(tipo);
		notificacion.setEmpleado(empleado);
		notificacion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		notificacion.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		notificacion.setUsuarioCrea(usuarioRegistra);
		notificacion.setFechaCrea(LocalDateTime.now());
		notificacion.setEnviadoApp(false);
		notificacion.setEnviadoCorreo(false);
		notificacion.setVisto(false);
		logger.info("[END] registrar");
		return notificacionDao.saveAndFlush(notificacion);
	}

	@Override
	public Optional<NotificacionTipo> obtenerTipoNotificacion(String codigo) {
		return notificacionTipoDao.findOneByCodigo(codigo);
	}

	@Override
	public Notificacion obtenerPorId(long id) {
		Optional<Notificacion> opt = notificacionDao.findById(id);
		if(opt.isPresent())
			return opt.get();
		return null;
	}

	@Override
	public List<Notificacion> listarNotificacionesPorTipoNoEnviadasCorreo(String codigoTipo) {
		logger.info("[BEGIN] listarNotificacionesPorTipoNoEnviadasCorreo " + codigoTipo);
		Optional<NotificacionTipo> opt = obtenerTipoNotificacion(codigoTipo);
		List<Notificacion> notificacionesPendientes = null;
		if(opt.isPresent()) {
			notificacionesPendientes = notificacionDao.listarNotificacionesPorTipoNoEnviadasCorreo(opt.get().getId());
		}
		notificacionesPendientes = notificacionesPendientes == null ? new ArrayList<>() : notificacionesPendientes;
		logger.info("[END] listarNotificacionesPorTipoNoEnviadasCorreo " + notificacionesPendientes.size());
		return notificacionesPendientes;
	}

	@Override
	public List<Notificacion> listarNotificacionesPorTipoNoEnviadasApp(String codigoTipo) {
		logger.info("[BEGIN] listarNotificacionesPorTipoNoEnviadasApp " + codigoTipo);
		Optional<NotificacionTipo> opt = obtenerTipoNotificacion(codigoTipo);
		List<Notificacion> notificacionesPendientes = null;
		if(opt.isPresent()) {
			 notificacionesPendientes = notificacionDao.listarNotificacionesPorTipoNoEnviadasApp(opt.get().getId());
		}
		notificacionesPendientes = notificacionesPendientes == null ? new ArrayList<>() : notificacionesPendientes;
		logger.info("[END] listarNotificacionesPorTipoNoEnviadasApp " + notificacionesPendientes.size());
		return notificacionesPendientes;
	}

	@Override
	public long obtenerCantidadNotificacionesNoVistasPorEmpleadoYTipo(long idEmpleado, long idTipo) {
		return notificacionDao.getCountOfNotViewedByEmpleadoAndTipo(idEmpleado, idTipo);
	}

	@Override
	public void enviarCorreoReporte(String titulo, String descripcion, String receptorEmail, String receptorNombre, String nombreArchivo,
			String contentType, byte[] archivo) {
		logger.info("[BEGIN] enviarCorreoReporte ");
		Mail mail = new Mail();
		mail.setAsunto(titulo);
		mail.setContenido(new HashMap<>());
		mail.getContenido().put("empleado", "Hola, " + receptorNombre);
		mail.getContenido().put("mensaje", descripcion);
		mail.setReceptor(receptorEmail.toString());
		mail.setEmisor("desarrollofc@confianza.pe");
		logger.error("archivo " + archivo.length);
		mail.getAdjuntos().add(new MailFile(nombreArchivo, contentType, archivo));
		emailUtil.enviarEmail(mail);
		logger.info("[END] enviarCorreoReporte ");
	}

	
	
	

}
