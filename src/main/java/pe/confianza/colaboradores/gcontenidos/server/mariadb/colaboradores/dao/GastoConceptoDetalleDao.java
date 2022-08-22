package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoDetalle;

@Repository
public interface GastoConceptoDetalleDao extends JpaRepository<GastoConceptoDetalle, Long> {
	
	@Query("SELECT c FROM GastoConceptoDetalle c WHERE c.concepto.id = ?1")
	List<GastoConceptoDetalle> listConceptoDetalle(Long idConcepto);
}
