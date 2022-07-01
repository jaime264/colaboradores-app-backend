package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadOperativa;

public interface UnidadOperativaDao extends JpaRepository<UnidadOperativa, Long>{

	@Query("SELECT u FROM UnidadOperativa u WHERE u.responsable.id = ?1")
	List<UnidadOperativa> listUnidadOperativa(Long idRepresentante);
	
}
