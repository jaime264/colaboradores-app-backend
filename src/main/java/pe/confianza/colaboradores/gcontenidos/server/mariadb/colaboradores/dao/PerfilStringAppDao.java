package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PerfilSpringApp;

@Repository
public interface PerfilStringAppDao extends JpaRepository<PerfilSpringApp, Long> {
	
	@Query("SELECT p FROM PerfilSpringApp p WHERE p.perfilSpring.id = ?1 AND p.estadoRegistro = 'A' AND p.perfilApp.estadoRegistro = 'A' ")
	List<PerfilSpringApp> listarPorPerfilSpring(long idPerfilString);

}
