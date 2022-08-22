package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.CentroCosto;

public interface CentroCostoDao extends JpaRepository<CentroCosto, Long> {
	
	@Query("SELECT ct FROM CentroCosto ct WHERE ct.Sucursal = ?1")
	public List<CentroCosto> obtenerCentroCostoByAgencia(String codAgencia);

}