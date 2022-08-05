package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteTipo;

@Repository
public interface ReporteTipoDao extends JpaRepository<ReporteTipo, Long>{
	
	@Query("SELECT rt FROM ReporteTipo rt WHERE rt.estadoRegistro = 'A'")
	List<ReporteTipo> listarActivos();
	
	Optional<ReporteTipo> findOneByCodigo(String codigo);

}
