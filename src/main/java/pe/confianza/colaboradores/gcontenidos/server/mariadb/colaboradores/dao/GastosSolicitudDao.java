package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastosSolicitud;

public interface GastosSolicitudDao extends JpaRepository<GastosSolicitud, Long>{

	@Query("select gs from GastosSolicitud gs where gs.empleado.id = ?1")
	public List<GastosSolicitud> obtenerTipoGastoByEmpleado(long idEmpleado);
}
