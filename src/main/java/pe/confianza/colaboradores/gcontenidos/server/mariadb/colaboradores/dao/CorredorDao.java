package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Corredor;

public interface CorredorDao extends JpaRepository<Corredor, Long>  {
	
	@Query("SELECT c FROM Corredor c WHERE c.representante.id = ?1")
	List<Corredor> listCorredor(Long idRepresentante);
	
	@Query("SELECT e FROM Empleado e inner join e.agencia a inner join a.corredor c WHERE e.id = ?1")
	Corredor corredorEmpledo(Long idEmpleado);
	
	@Query("SELECT c FROM Corredor c WHERE c.estadoRegistro = 'A' AND c.codigoTerritorio = ?1 ORDER BY c.descripcion ASC ")
	List<Corredor> listarhabilitadosPorTerritorio(String codigoTerritorio);
	
	@Query("SELECT c FROM Corredor c WHERE c.estadoRegistro = 'A' AND c.codigo = ?1 ORDER BY c.descripcion ASC ")
	List<Corredor> listarhabilitadosPorCodigo(String codigo);
}
