package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoPeriodoGasto;

@Repository
public interface PresupuestoPeriodoGastoDao extends JpaRepository<PresupuestoPeriodoGasto, Long> {

	@Query("SELECT p FROM PresupuestoPeriodoGasto p where p.estadoRegistro = 'A' AND p.actual = true AND p.presupuestoAgencia.agencia = ?1 AND p.presupuestoAgencia.presupuestoConcepto.id = ?2 ")
	List<PresupuestoPeriodoGasto> buscarPeriodoActual(long idAgencia, long idConceptoDetalle);
}
