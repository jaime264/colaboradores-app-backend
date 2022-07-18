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

	@Query("SELECT em FROM Empleado em WHERE em.codigoNivel1 = ?1 and em.codigoNivel2 = ?1")
	public List<Empleado> findByCodigoJefe(Long codigoNivel1);
	
	@Query("SELECT em FROM Empleado em WHERE em.codigoNivel1 = ?1 or em.codigoNivel2 = ?1 and em.estadoRegistro = 'A'")
	public List<Empleado> findByCodigoAprobador(long codigoAprobador);

	/*@Query("SELECT vp FROM VacacionProgramacion vp inner join vp.periodo pv inner join pv.empleado e inner join e.agencia a where vp.estadoRegistro = 'A' and vp.idEstado = 2 and e.id = ?1")
	public List<VacacionProgramacion> findPeriodosByEmpleado(Long idEmpleado);*/
	
	@Query("SELECT vp FROM VacacionProgramacion vp where vp.estadoRegistro = 'A' and vp.idEstado = 2 and vp.periodo.empleado.id = ?1")
	public List<VacacionProgramacion> findPeriodosByEmpleado(Long idEmpleado);

	@Query(value = "Select * from empleado e where MONTH(e.fecha_nacimiento) = ?1 and DAY (e.fecha_nacimiento) = ?2", nativeQuery = true)
	public List<Empleado> findfechaNacimientoDeHoy(int mes, int dia);

	@Query(value = "SELECT COUNT(*) FROM empleado e INNER JOIN puesto p on e.id_puesto = p.id where e.codigo_unidad_negocio = ?1 AND p.descripcion like ?2", nativeQuery = true)
	int obtenerCantidadEmpleadosPorPuestoYPorUnidadNegocio(long unidadNegocio, String puesto);

	@Query(value = "select DISTINCT e.id, CONCAT(e.nombres, ' ', e.apellido_paterno, ' ', e.apellido_paterno) "
			+ "from empleado e where e.codigo_nivel1 = ?1 or e.codigo_nivel2 = ?2"
			+ "", nativeQuery = true)
	List<Map<String, String>> findNombreByCodigoN1(String codigoNivel1, String codigoNivel2);
	
	@Query(value = "select DISTINCT p.id, p.descripcion "
			+ "from empleado e inner join puesto p on e.id_puesto = p.id "
			+ "where e.codigo_nivel1 = ?1 or e.codigo_nivel2 = ?2", nativeQuery = true)
	List<Map<String, String>> findPuestoByCodigoN1(String codigoNivel1, String codigoNivel2);
	
	@Query(value = "select DISTINCT a.id, a.descripcion "
			+ "from empleado e inner join agencia a  on e.id_agencia = a.id "
			+ "where e.codigo_nivel1 = ?1 or e.codigo_nivel2 = ?2", nativeQuery = true)
	List<Map<String, String>> findAgenciaByCodigoN1(String codigoNivel1, String codigoNivel2);
	
	@Query(value ="select DISTINCT t.id, t.descripcion from empleado e "
			+ "inner join agencia a  on e.id_agencia = a.id "
			+ "inner join corredor c on a.id_corredor = c.id "
			+ "inner join territorio t on c.id_territorio = t.id "
			+ "where e.codigo_nivel1 = ?1 or e.codigo_nivel2 = ?2", nativeQuery = true)
	List<Map<String, String>> findTerritorioByCodigoN1(String codigoNivel1, String codigoNivel2);
	
	@Query(value = "select DISTINCT c.id, c.descripcion from empleado e "
			+ "inner join agencia a  on e.id_agencia = a.id "
			+ "inner join corredor c on a.id_corredor = c.id "
			+ "where e.codigo_nivel1 = ?1 or e.codigo_nivel2 = ?2", nativeQuery = true)
	List<Map<String, String>> findCorredorByCodigoN1(String codigoNivel1, String codigoNivel2);
	
	@Query(value = "select DISTINCT a.id, uo.descripcion from empleado e "
			+ "inner join agencia a  on e.id_agencia = a.id "
			+ "inner join unidad_operativa uo on a.id = uo.id_agencia "
			+ "where e.codigo_nivel1 = ?1 or e.codigo_nivel2 = ?2", nativeQuery = true)
	List<Map<String, String>> findAreaByCodigoN1(String codigoNivel1, String codigoNivel2);

	
	@Query(value = "select (CASE WHEN SUM(cantidad_subordinados) IS NULL THEN 0 ELSE SUM(cantidad_subordinados) end)  from  vacaciones.aprobador_vacaciones_primer_nivel where id = ?1", nativeQuery = true)
	int obtenerCantidadSuborninadosNivel1(long idEmpleado);
	
	@Query(value = "select (CASE WHEN SUM(cantidad_subordinados) IS NULL THEN 0 ELSE SUM(cantidad_subordinados) end)  from  vacaciones.aprobador_vacaciones_segundo_nivel where id = ?1", nativeQuery = true)
	int obtenerCantidadSuborninadosNivel2(long idEmpleado);
	

	@Query(value =  "SELECT COUNT(*) FROM empleado_red_operaciones", nativeQuery = true)
	long contarEmpleadosRedOperaciones();

}
