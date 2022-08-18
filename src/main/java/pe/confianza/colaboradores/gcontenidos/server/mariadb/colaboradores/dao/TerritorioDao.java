package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Territorio;

@Repository
public interface TerritorioDao extends JpaRepository<Territorio, Long> {
	
	
	@Query("SELECT t FROM Territorio t WHERE t.estadoRegistro = 'A' ORDER BY t.descripcion ASC")
	List<Territorio> listarHabilitados();
	
	@Query("SELECT t FROM Territorio t WHERE t.estadoRegistro = 'A' AND t.codigo = ?1 ORDER BY t.descripcion ASC")
	List<Territorio> listarHabilitadosPorCodigo(String codigo);
}
