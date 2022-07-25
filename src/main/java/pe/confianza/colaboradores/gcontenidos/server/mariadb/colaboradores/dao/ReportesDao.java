package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteColaboradores;


public interface ReportesDao extends JpaRepository<ReporteColaboradores, Long> {
	
	@Query("Select c from ReporteColaboradores c where c.codigoNivel1 = ?1 or c.codigoNivel2 = ?1 ")
	Page<ReporteColaboradores> reporteColaboradores(String codigoNivel, Pageable pageable);
}
