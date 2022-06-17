package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;

@Repository
public interface NotificacionTipoDao extends JpaRepository<NotificacionTipo, Long> {

	Optional<NotificacionTipo> findOneByCodigo(String codigo);
	
	@Query("Select nt FROM NotificacionTipo nt WHERE nt.estadoRegistro = 'A' ")
	List<NotificacionTipo> listarActivos();
}
