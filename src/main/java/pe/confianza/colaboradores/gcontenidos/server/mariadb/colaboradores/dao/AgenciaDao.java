package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;

@Repository
public interface AgenciaDao extends JpaRepository<Agencia, Long> {

	Optional<Agencia> findOneByCodigo(String codigo);
	
	@Query("SELECT a FROM Agencia a WHERE a.estadoRegistro = 'A' ORDER BY a.descripcion ASC")
	List<Agencia> listarHabilitados();
	
	@Query("SELECT a FROM Agencia a WHERE a.estadoRegistro = 'A' AND a.codigoCorredor = ?1 ORDER BY a.descripcion ASC")
	List<Agencia> listarHabilitadosPorCorredor(String codigoCorredor);
	
	@Query("SELECT a FROM Agencia a WHERE a.estadoRegistro = 'A' AND a.codigo = ?1 ORDER BY a.descripcion ASC")
	List<Agencia> listarAgenciaPorCodigo(String codigo);
	
	
}
