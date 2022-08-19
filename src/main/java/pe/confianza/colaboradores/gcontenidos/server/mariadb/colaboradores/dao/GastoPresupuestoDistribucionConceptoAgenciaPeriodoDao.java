package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConceptoAgenciaPeriodo;

@Repository
public interface GastoPresupuestoDistribucionConceptoAgenciaPeriodoDao extends JpaRepository<GastoPresupuestoDistribucionConceptoAgenciaPeriodo, Long> {

	@Query("SELECT p FROM GastoPresupuestoDistribucionConceptoAgenciaPeriodo p where p.estadoRegistro = 'A' AND p.actual = true AND p.distribucionAgencia.agencia = ?1 AND p.distribucionAgencia.distribucionConcepto.conceptoDetalle.id = ?2 ")
	List<GastoPresupuestoDistribucionConceptoAgenciaPeriodo> buscarPeriodoActual(long idAgencia, long idConceptoDetalle);
}
