package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Empleado;

import java.lang.String;
import java.util.Optional;

@Repository
public interface EmpleadoDao extends JpaRepository<Empleado, Long>{
	
	Optional<Empleado> findOneByUsuarioBT(String usuarioBT);
	

}
