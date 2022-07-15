package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.EmpleadoAcceso;

@Repository
public interface EmpleadoAccesoDao extends JpaRepository<EmpleadoAcceso, String>{
	
	List<EmpleadoAcceso> findByEmpleadoUsuariobt(String empleadoUsuariobt);

}
