package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteAcceso;

@Repository
public interface ReporteAccesoDao extends JpaRepository<ReporteAcceso, Long> {

	@Query("SELECT ra FROM ReporteAcceso ra WHERE ra.puesto.id = ?1 AND ra.tipo.id = ?2")
	List<ReporteAcceso> buscarPorPuestoYReporte(long idPuesto, long idReporte);

	@Query("SELECT ra FROM ReporteAcceso ra where ra.estadoRegistro = 'A' order by ra.puesto.descripcion ASC")
	Page<ReporteAcceso> listar(Pageable pageable);

	@Query("SELECT ra FROM ReporteAcceso ra where ra.puesto.id = ?1 AND ra.estadoRegistro = 'A' order by ra.puesto.descripcion ASC")
	Page<ReporteAcceso> listar(long idPuesto, Pageable pageable);

	@Query(value = "select count(*) from empleado e inner join puesto p on e.id_puesto = p.id inner join reporte_acceso ra on p.id = ra.id_puesto \r\n"
			+ "where e.codigo = ?1", nativeQuery = true)
	int cantidadEmpleadosAcceso(String codigoEmpleado);

}
