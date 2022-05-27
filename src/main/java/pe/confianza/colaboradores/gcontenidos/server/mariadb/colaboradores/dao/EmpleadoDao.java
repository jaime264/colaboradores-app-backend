package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

@Repository
public interface EmpleadoDao extends JpaRepository<Empleado, Long>{
	
	Optional<Empleado> findOneByUsuarioBT(String usuarioBT);
	
	@Query("SELECT em FROM Empleado em WHERE em.codigo = ?1")
	public List<Empleado> findByCodigo(Long codigo);
	
	@Query("SELECT em FROM Empleado em WHERE em.codigoNIvel1 = ?1")
	public List<Empleado> findByCodigoJefe(Long codigoNivel1);

	@Query("SELECT vp FROM VacacionProgramacion vp inner join vp.periodo pv inner join pv.empleado e where e.id = ?1")
	public List<VacacionProgramacion> findPeriodosByEmpleado(Long idEmpleado);

}
