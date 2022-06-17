package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;

public interface NotificacionService {
	
	List<NotificacionTipo> obtenerTipos();
	
	List<Notificacion> consultar(Empleado empleado, NotificacionTipo tipo);
	
	Optional<NotificacionTipo> obtener(String codigo);
	
	Optional<Notificacion> obtener(long id);
	
	Notificacion actualizar(Notificacion notificacion, String usuarioActualiza);

}
