package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.confianza.colaboradores.gcontenidos.server.bean.IReporteMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;

public interface ReporteMetaDao extends JpaRepository<VacacionMeta, Long> {

	@Query(value = "select 'META' as categoria, SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, SUM(vp.dias_gozados) as diasgozados, 'opcional' as opcional from "
			+ "	vacacion_meta vm inner join vacacion_periodo vp on 	vm.id_empleado = vp.id_empleado "
			+ "where 	(e.codigo_nivel1 = :codigo "
			+ " or e.codigo_nivel2 = :codigo) and vm.anio = :anio", nativeQuery = true)
	List<IReporteMeta> reporteMeta(@Param("codigo") String codigoNivel, @Param("anio") int anio);

	@Query(value = "select " + "	t.descripcion as categoria, " + "	SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, " + "	SUM(vp.dias_gozados) as diasgozados " + "from "
			+ "	vacacion_meta vm " + "inner join vacacion_periodo vp on " + "	vm.id_empleado = vp.id_empleado "
			+ "inner join empleado e on " + "	e.id = vp.id_empleado " + "	inner join agencia a on "
			+ "	a.id = e.id_agencia " + "inner join corredor c " + "on c.id = a.id_corredor "
			+ "inner join territorio t on t.id = c.id_territorio "
			+ "WHERE vm.anio = :anio and (e.codigo_nivel1 = :codigo or e.codigo_nivel2 = :codigo) "
			+ "GROUP by t.descripcion ", nativeQuery = true)
	List<IReporteMeta> reporteMetaTerritorio(@Param("codigo") String codigoNivel, @Param("anio") int anio);

	@Query(value = "select " + "	tcc.descripcion as categoria, " + "	SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, " + "	SUM(vp.dias_gozados) as diasgozados " + "from "
			+ "	vacacion_meta vm " + "inner join vacacion_periodo vp on " + "	vm.id_empleado = vp.id_empleado "
			+ "inner join empleado e on " + "	e.id = vp.id_empleado " + "inner join division d on "
			+ "	e.codigo_gerente_division = d.codigo_spring " + "inner join puesto p on " + "	e.id_puesto = p.id "
			+ "inner join tipo_clasificacion_cargo tcc on " + "	p.id_tipo_clasificacion = tcc.id "
			+ "WHERE vm.anio = :anio and (e.codigo_nivel1 = :codigo or e.codigo_nivel2 = :codigo) "
			+ "GROUP BY tcc.descripcion ", nativeQuery = true)
	List<IReporteMeta> reporteMetaColectivo(@Param("codigo") String codigoNivel, @Param("anio") int anio);

	@Query(value = "select " + "	tcc.descripcion as categoria, " + "	SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, " + "	SUM(vp.dias_gozados) as diasgozados " + "from "
			+ "	vacacion_meta vm " + "inner join vacacion_periodo vp on " + "	vm.id_empleado = vp.id_empleado "
			+ "inner join empleado e on " + "	e.id = vp.id_empleado " + "inner join agencia a on "
			+ "	a.id = e.id_agencia " + "inner join corredor c " + "on " + "	c.id = a.id_corredor "
			+ "inner join territorio t on " + "	t.id = c.id_territorio " + "inner join division d on "
			+ "	e.codigo_gerente_division = d.codigo_spring " + "inner join puesto p on " + "	e.id_puesto = p.id "
			+ "inner join tipo_clasificacion_cargo tcc on " + "	p.id_tipo_clasificacion = tcc.id " + "WHERE "
			+ "	vm.anio = :anio and t.descripcion = :filtro " + "	and (e.codigo_nivel1 = :codigo "
			+ "		or e.codigo_nivel2 = :codigo) " + "GROUP by " + "	tcc.descripcion  " + "", nativeQuery = true)
	List<IReporteMeta> reporteMetaTerritorioColectivo(@Param("codigo") String codigoNivel, @Param("anio") int anio,
			@Param("filtro") String filtro);

	@Query(value = "select " + "	d.descripcion as categoria, " + "	SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, " + "	SUM(vp.dias_gozados) as diasgozados " + "from "
			+ "	vacacion_meta vm " + "inner join vacacion_periodo vp on " + "	vm.id_empleado = vp.id_empleado "
			+ "inner join empleado e on " + "	e.id = vp.id_empleado " + "inner join division d on "
			+ "	e.codigo_gerente_division = d.codigo_spring " + "inner join puesto p on " + "	e.id_puesto = p.id "
			+ "inner join tipo_clasificacion_cargo tcc on " + "	p.id_tipo_clasificacion = tcc.id " + "WHERE "
			+ "	vm.anio = :anio " + "	and tcc.descripcion = :filtro " + "	and (e.codigo_nivel1 = :codigo "
			+ "		or e.codigo_nivel2 = :codigo) " + "GROUP BY " + "	d.descripcion", nativeQuery = true)
	List<IReporteMeta> reporteMetaColectivoDivision(@Param("codigo") String codigoNivel, @Param("anio") int anio,
			@Param("filtro") String filtro);

}
