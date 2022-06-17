package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.NotificacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.NotificacionTipoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;

@Service
public class NotificacionServiceImpl implements NotificacionService{

	@Autowired
	private NotificacionTipoDao notificacionTipoDao;
	
	@Autowired
	private NotificacionDao notificacionDao;
	
	@Override
	public List<NotificacionTipo> obtenerTipos() {
		List<NotificacionTipo> lst = notificacionTipoDao.listarActivos();
		lst = lst == null ? new ArrayList<>() : lst;
		return lst;
	}

	@Override
	public List<Notificacion> consultar(Empleado empleado, NotificacionTipo tipo) {
		List<Notificacion> lst = notificacionDao.consultar(empleado.getId(), tipo.getId());
		lst = lst == null ? new ArrayList<>() : lst;
		return lst;
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
		return notificacionDao.save(notificacion);
	}

}
