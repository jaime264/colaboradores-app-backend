package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoGeneralGasto;

@Repository
public interface PresupuestoGeneralGastoDao extends JpaRepository<PresupuestoGeneralGasto, Long> {

	@Query("SELECT p FROM PresupuestoGeneralGasto p WHERE p.estadoRegistro = 'A' order by p.id DESC")
	List<PresupuestoGeneralGasto> listarHabilitados();
	
	
	Optional<PresupuestoGeneralGasto> findOneByCodigo(long codigo);
}
