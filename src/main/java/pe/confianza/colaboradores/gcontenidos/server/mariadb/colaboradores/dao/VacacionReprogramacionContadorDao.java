package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionReprogramacionContador;

@Repository
public interface VacacionReprogramacionContadorDao extends JpaRepository<VacacionReprogramacionContador, Long> {

	@Query("SELECT c FROM VacacionReprogramacionContador c WHERE c.empleado.id = ?1 AND c.anio = ?2 AND c.estadoRegistro = 'A'")
	Optional<VacacionReprogramacionContador> obtenerPorEmpleadoAndAnio(long idEmpleado, int anio);
	
}
