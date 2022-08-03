package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteColaboradores;

public interface ReportesDao extends JpaRepository<ReporteColaboradores, Long> {

	@Query(value = "select * from reporte_seguimiento rs where rs.codigo_nivel1 = ?1 or rs.codigo_nivel2 = ?1", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradores(String codigoNivel, Pageable pageable);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 = :codigo or rs.codigo_nivel2 = :codigo) and rs.codigo in (:filtro)", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresCodigo(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 = :codigo or rs.codigo_nivel2 = :codigo) and rs.nombre_completo in (:filtro)", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresNombre(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 = :codigo or rs.codigo_nivel2 = :codigo) and rs.puesto in (:filtro)", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresPuesto(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 = :codigo or rs.codigo_nivel2 = :codigo) and rs.corredor in (:filtro)", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresCorredor(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 = :codigo or rs.codigo_nivel2 = :codigo) and rs.territorio in (:filtro)", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresTerritorio(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 = :codigo or rs.codigo_nivel2 = :codigo) and rs.agencia in (:filtro)", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresAgencia(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable);
	
	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 = :codigo or rs.codigo_nivel2 = :codigo) and fecha_ingreso BETWEEN  :inicio AND :fin", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresIngreso(@Param("codigo") String codigoNivel,
			@Param("inicio")LocalDate fechaInicio, @Param("fin")LocalDate fechaFin, Pageable pageable);
	
	@Query("Select c from ReporteColaboradores c where c.codigoNivel1 = ?1 or c.codigoNivel2 = ?1 ")
	List<ReporteColaboradores> reporteColaboradoresList(String codigoNivel);
}
