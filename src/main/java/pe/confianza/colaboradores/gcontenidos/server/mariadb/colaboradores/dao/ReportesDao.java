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

	@Query(value = "select * from reporte_seguimiento rs where rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo and rs.anio = :anio order by rs.codigo desc", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradores(@Param("codigo") String codigo, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and rs.codigo in (:filtro) and rs.anio = :anio", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresCodigo(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and rs.nombre_completo in (:filtro) and rs.anio = :anio", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresNombre(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and rs.puesto in (:filtro) and rs.anio = :anio", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresPuesto(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and rs.corredor in (:filtro) and rs.anio = :anio", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresCorredor(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and rs.territorio in (:filtro) and rs.anio = :anio order by rs.codigo :orden", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresTerritorio(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and rs.agencia in (:filtro) and rs.anio = :anio", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresAgencia(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and rs.division in (:filtro) and rs.anio = :anio", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresDivision(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and rs.colectivo in (:filtro) and rs.anio = :anio "
			+ "", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresColectivo(@Param("codigo") String codigoNivel,
			@Param("filtro") List<String> filtro, Pageable pageable, @Param("anio") int anio);

	@Query(value = "select * from reporte_seguimiento rs where (rs.codigo_nivel1 like %:codigo or rs.codigo_nivel2 like %:codigo) and fecha_ingreso BETWEEN  :inicio AND :fin and rs.anio = :anio", nativeQuery = true)
	Page<ReporteColaboradores> reporteColaboradoresIngreso(@Param("codigo") String codigoNivel,
			@Param("inicio") LocalDate fechaInicio, @Param("fin") LocalDate fechaFin, Pageable pageable, @Param("anio") int anio);

	@Query(value = "Select * from reporte_seguimiento c where c.codigo_nivel1 like %:codigo or c.codigo_nivel2 like %:codigo and c.anio =:anio" , nativeQuery = true)
	List<ReporteColaboradores> reporteColaboradoresList(@Param("codigo") String codigo, @Param("anio") int anio);
}
