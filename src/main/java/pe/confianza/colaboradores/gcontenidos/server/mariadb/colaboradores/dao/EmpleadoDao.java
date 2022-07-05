package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

@Repository
public interface EmpleadoDao extends JpaRepository<Empleado, Long> {

	Optional<Empleado> findOneByUsuarioBT(String usuarioBT);

	@Query("SELECT em FROM Empleado em WHERE em.codigo = ?1")
	public List<Empleado> findByCodigo(Long codigo);

	@Query("SELECT em FROM Empleado em WHERE em.codigoNivel1 = ?1")
	public List<Empleado> findByCodigoJefe(Long codigoNivel1);

	@Query("SELECT vp FROM VacacionProgramacion vp inner join vp.periodo pv inner join pv.empleado e inner join e.agencia a where e.id = ?1")
	public List<VacacionProgramacion> findPeriodosByEmpleado(Long idEmpleado);

	@Query(value = "Select * from empleado e where MONTH(e.fecha_nacimiento) = ?1 and DAY (e.fecha_nacimiento) = ?2", nativeQuery = true)
	public List<Empleado> findfechaNacimientoDeHoy(int mes, int dia);

	@Query(value = "SELECT COUNT(*) FROM empleado e INNER JOIN puesto p on e.id_puesto = p.id where e.codigo_unidad_negocio = ?1 AND p.descripcion like ?2", nativeQuery = true)
	int obtenerCantidadEmpleadosPorPuestoYPorUnidadNegocio(long unidadNegocio, String puesto);

	@Query(value = "Select DISTINCT e.id, CONCAT(e.nombres, ' ',e.apellido_paterno,' ', e.apellido_materno) as descripcion from vacacion_programacion vp "
			+ "inner join vacacion_periodo vp2 on vp.id_periodo = vp2.id inner join empleado e on vp2.id_empleado = vp2.id_empleado where "
			+ "e.codigo_nivel1 = ?1", nativeQuery = true)
	List<Map<String, String>> findNombreByCodigoN1(String codigoNivel1);
	
	@Query(value = "Select DISTINCT p.id, p.descripcion from vacacion_programacion vp inner join vacacion_periodo vp2 "
			+ "on vp.id_periodo = vp2.id inner join empleado e on vp2.id_empleado = vp2.id_empleado "
			+ "inner join puesto p on e.id_puesto = p.id where e.codigo_nivel1 = ?1", nativeQuery = true)
	List<Map<String, String>> findPuestoByCodigoN1(String codigoNivel1);
	
	@Query(value = "Select DISTINCT a.id, a.descripcion from vacacion_programacion vp inner join vacacion_periodo vp2 "
			+ "on vp.id_periodo = vp2.id inner join empleado e on vp2.id_empleado = vp2.id_empleado inner join agencia a "
			+ "on e.id_agencia = a.id  where e.codigo_nivel1 = ?1", nativeQuery = true)
	List<Map<String, String>> findAgenciaByCodigoN1(String codigoNivel1);
	
	@Query(value = "Select DISTINCT t.id, t.descripcion from vacacion_programacion vp inner join vacacion_periodo vp2 "
			+ "on vp.id_periodo = vp2.id inner join empleado e on vp2.id_empleado = vp2.id_empleado inner join corredor c "
			+ "on c.id_empleado_representante = e.id inner join territorio t on c.id_territorio = t.id where e.codigo_nivel1 = ?1 "
			+ "", nativeQuery = true)
	List<Map<String, String>> findTerritorioByCodigoN1(String codigoNivel1);
	
	@Query(value = "Select DISTINCT c.id, c.descripcion  from vacacion_programacion vp inner join vacacion_periodo vp2 on "
			+ "vp.id_periodo = vp2.id inner join empleado e on vp2.id_empleado = vp2.id_empleado inner join corredor c on "
			+ "c.id_empleado_representante = e.id where e.codigo_nivel1 = ?1 "
			+ "", nativeQuery = true)
	List<Map<String, String>> findCorredorByCodigoN1(String codigoNivel1);
	
	@Query(value = "Select DISTINCT uo.id, uo.descripcion from vacacion_programacion vp inner join vacacion_periodo vp2 on "
			+ "vp.id_periodo = vp2.id inner join empleado e on vp2.id_empleado = vp2.id_empleado inner join unidad_operativa uo "
			+ "on uo.id_empleado_responsable = e.id where e.codigo_nivel1 = ?1"
			+ "", nativeQuery = true)
	List<Map<String, String>> findAreaByCodigoN1(String codigoNivel1);
	
	@Query(value = "select (CASE WHEN SUM(cantidad_subordinados) IS NULL THEN 0 ELSE SUM(cantidad_subordinados) end)  from  vacaciones.aprobador_vacaciones_primer_nivel where id = ?1", nativeQuery = true)
	int obtenerCantidadSuborninadosNivel1(long idEmpleado);
	
	@Query(value = "select (CASE WHEN SUM(cantidad_subordinados) IS NULL THEN 0 ELSE SUM(cantidad_subordinados) end)  from  vacaciones.aprobador_vacaciones_segundo_nivel where id = ?1", nativeQuery = true)
	int obtenerCantidadSuborninadosNivel2(long idEmpleado);

}
