package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoAnual;

@Repository
public interface GastoPresupuestoAnualDao extends JpaRepository<GastoPresupuestoAnual, Long> {

	@Query("SELECT gp FROM GastoPresupuestoAnual gp WHERE gp.estadoRegistro = 'A' order by gp.id DESC")
	List<GastoPresupuestoAnual> listarHabilitados();
	
	
	Optional<GastoPresupuestoAnual> findOneByCodigo(long codigo);
}
