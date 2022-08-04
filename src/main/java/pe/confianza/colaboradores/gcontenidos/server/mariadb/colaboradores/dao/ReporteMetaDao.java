package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.bean.IReporteMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;

public interface ReporteMetaDao extends JpaRepository<VacacionMeta, Long> {

	@Query(value = "select 'META' as categoria, SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, SUM(vp.dias_gozados) as diasgozados, 'opcional' as opcional from "
			+ "	vacacion_meta vm inner join vacacion_periodo vp on 	vm.id_empleado = vp.id_empleado "
			+ "where 	vm.anio = 2023", nativeQuery = true)
	List<IReporteMeta> reporteMeta();

	@Query(value = "select t.descripcion as categoria, SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, SUM(vp.dias_gozados) as diasgozados, 'opcional' as opcional from "
			+ "	vacacion_meta vm inner join vacacion_periodo vp on vm.id_empleado = vp.id_empleado "
			+ "inner join empleado e on e.id = vp.id_empleado inner join agencia a on "
			+ "	a.id = e.id_agencia inner join corredor c on c.id = a.id_corredor "
			+ "inner join territorio t on t.id = c.id_territorio WHERE vm.anio = 2023 "
			+ "GROUP by t.id ", nativeQuery = true)
	List<IReporteMeta> reporteMetaTerritorio();

	@Query(value = "select p.descripcion as categoria, SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, SUM(vp.dias_gozados) as diasgozados, 'opcional' as opcional from "
			+ "	vacacion_meta vm inner join vacacion_periodo vp on 	vm.id_empleado = vp.id_empleado "
			+ "inner join empleado e on e.id = vp.id_empleado inner join puesto p on "
			+ "	p.id = e.id_puesto inner join agencia a on 	a.id = e.id_agencia "
			+ "inner join corredor c on	c.id = a.id_corredor inner join territorio t on "
			+ "	t.id = c.id_territorio WHERE vm.anio = 2023 GROUP by 	p.descripcion", nativeQuery = true)
	List<IReporteMeta> reporteMetaColectivo();

	@Query(value = "select p.descripcion as categoria, SUM(vm.meta) as meta, "
			+ "	SUM(vm.meta_inicial) as metainicial, SUM(vp.dias_gozados) as diasgozados, "
			+ "	t.descripcion as opcional from 	vacacion_meta vm inner join vacacion_periodo vp on "
			+ "	vm.id_empleado = vp.id_empleado inner join empleado e on e.id = vp.id_empleado "
			+ "inner join puesto p on p.id = e.id_puesto inner join agencia a on "
			+ "	a.id = e.id_agencia inner join corredor c on c.id = a.id_corredor "
			+ "inner join territorio t on t.id = c.id_territorio WHERE	vm.anio = 2023 GROUP by "
			+ "	p.descripcion, t.id", nativeQuery = true)
	List<IReporteMeta> reporteMetaTerritorioColectivo();
}
