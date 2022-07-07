package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.FuncionalidadAcceso;

@Repository
public interface FuncionalidadAccesoDao extends JpaRepository<FuncionalidadAcceso, Long>{
	
	@Query("SELECT f FROM FuncionalidadAcceso f WHERE f.perfilApp.id = ?1 AND f.estadoRegistro = 'A' AND f.funcionalidad.estadoRegistro = 'A' ")
	List<FuncionalidadAcceso> listarPorPerfilApp(long idPerfilApp);

}
