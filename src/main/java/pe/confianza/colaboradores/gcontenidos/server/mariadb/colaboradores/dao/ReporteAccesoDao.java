package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteAcceso;

@Repository
public interface ReporteAccesoDao extends JpaRepository<ReporteAcceso, Long>{
	
	@Query("SELECT ra FROM ReporteAcceso ra WHERE ra.puesto.id = ?1 AND ra.tipo.id = ?2")
	List<ReporteAcceso> buscarPorPuestoYReporte(long idPuesto, long idReporte);
	
	@Query("SELECT ra FROM ReporteAcceso ra where ra.estadoRegistro = 'A' order by ra.puesto.descripcion ASC")
	Page<ReporteAcceso> listar(Pageable pageable);
	
	@Query("SELECT ra FROM ReporteAcceso ra where ra.puesto.id = ?1 AND ra.estadoRegistro = 'A' order by ra.puesto.descripcion ASC")
	Page<ReporteAcceso> listar(long idPuesto, Pageable pageable);

}
