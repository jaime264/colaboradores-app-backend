package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;

@Repository
public interface PublicacionAppDao extends JpaRepository<Publicacion, Long> {
	
	@Query("SELECT pb FROM Publicacion pb WHERE pb.activo = ?1 order by pb.id desc")
	public List<Publicacion> listByActivo(Boolean activo);
	

}
