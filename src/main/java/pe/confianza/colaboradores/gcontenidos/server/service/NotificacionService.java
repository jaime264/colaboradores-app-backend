package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;

public interface NotificacionService {
	
	List<NotificacionTipo> obtenerTipos();
	
	Page<Notificacion> consultar(Empleado empleado, NotificacionTipo tipo, int numeroPagina, int tamanioPagina);
	
	Optional<NotificacionTipo> obtener(String codigo);
	
	Optional<Notificacion> obtener(long id);
	
	Notificacion registrar(String titulo, String descripcion, String extraData, NotificacionTipo tipo, Empleado empleado, String usuarioRegistra);
	
	Notificacion actualizar(Notificacion notificacion, String usuarioActualiza);
	
	List<Notificacion> listarNotificacionesNoEnviadasApp();
	
	List<Notificacion> listarNotificacionesNoEnviadasCorreo();
	
	void enviarNotificacionApp(Notificacion notificacion);
	
	void enviarNotificacionCorreo(Notificacion notificacion);
	
	Optional<NotificacionTipo> obtenerTipoNotificacion(String codigo);
	
	Notificacion obtenerPorId(long id);
	
	List<Notificacion> listarNotificacionesPorTipoNoEnviadasCorreo(String codigoTipo);
	
	List<Notificacion> listarNotificacionesPorTipoNoEnviadasApp(String codigoTipo);

}
