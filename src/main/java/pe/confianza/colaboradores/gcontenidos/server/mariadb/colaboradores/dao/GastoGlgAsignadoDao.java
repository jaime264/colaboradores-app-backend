package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoGlgAsignado;

@Repository
public interface GastoGlgAsignadoDao extends JpaRepository<GastoGlgAsignado, Long> {
	
	@Query("SELEC g FROM GastoGlgAsignado g where g.codigoEmpleado = ?1 AND g.estadoRegistro = 'A' ")
	List<GastoGlgAsignado> buscarPorCodigoEmpleado(long codigoEmpleado);

}
