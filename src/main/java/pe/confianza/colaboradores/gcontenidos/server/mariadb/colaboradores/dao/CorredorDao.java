package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Corredor;

public interface CorredorDao extends JpaRepository<Corredor, Long>  {
	
	@Query("SELECT c FROM Corredor c WHERE c.representante.id = ?1")
	List<Corredor> listCorredor(Long idRepresentante);
}
