package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Division;


public interface DivisionDao extends JpaRepository<Division, Long>  {
	
	@Query("SELECT d FROM Division d WHERE d.codigoSpring = ?1")
	List<Division> listDivisionByCodigoSpring(int codigoSpring);
	

}
