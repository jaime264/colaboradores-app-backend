package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;

@Repository
public interface NotificacionDao extends JpaRepository<Notificacion, Long> {
	
	@Query("SELECT n FROM Notificacion n WHERE n.empleado.id = ?1 AND n.tipo.id = ?2 AND n.estadoRegistro = 'A' ORDER BY fechaCrea desc")
	List<Notificacion> consultar(long idEmpledo, long idTipo);

}
