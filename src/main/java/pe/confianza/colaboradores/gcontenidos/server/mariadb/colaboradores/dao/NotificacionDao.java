package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;

@Repository
public interface NotificacionDao extends JpaRepository<Notificacion, Long> {
	
	@Query("SELECT n FROM Notificacion n WHERE n.empleado.id = ?1 AND n.tipo.id = ?2 AND n.estadoRegistro = 'A' ORDER BY fechaCrea desc")
	Page<Notificacion> consultar(long idEmpledo, long idTipo, Pageable pageable);
	
	@Query("SELECT n FROM Notificacion n where n.estadoRegistro = 'A' AND n.enviadoCorreo = false ORDER BY fechaCrea desc")
	List<Notificacion> listarNotificacionesNoEnviadasCorreo();
	
	@Query("SELECT n FROM Notificacion n where n.estadoRegistro = 'A' AND n.enviadoApp = false ORDER BY fechaCrea desc")
	List<Notificacion> listarNotificacionesNoEnviadasApp();
	
	@Query("SELECT n FROM Notificacion n where n.estadoRegistro = 'A' AND n.enviadoCorreo = false AND n.tipo.id = ?1 ORDER BY fechaCrea desc")
	List<Notificacion> listarNotificacionesPorTipoNoEnviadasCorreo(long idTipo);
	
	@Query("SELECT n FROM Notificacion n where n.estadoRegistro = 'A' AND n.enviadoApp = false AND n.tipo.id = ?1 ORDER BY fechaCrea desc")
	List<Notificacion> listarNotificacionesPorTipoNoEnviadasApp(long idTipo);
	
	@Query("SELECT COUNT(n) FROM Notificacion n where n.empleado.id = ?1  AND n.tipo.id = ?2 AND n.visto = false AND n.estadoRegistro = 'A'")
	long getCountOfNotViewedByEmpleadoAndTipo(long idEmpleado, long idTipo);
	
	

}
