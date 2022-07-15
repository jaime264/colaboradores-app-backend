package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;

@Repository
public interface PublicacionAppDao extends JpaRepository<Publicacion, Long> {
	
	@Query("SELECT pb FROM Publicacion pb WHERE pb.activo = ?1 order by pb.id desc")
	public List<Publicacion> listByActivo(Boolean activo);
	
	@Query(value = "select e.id, e.usuariobt\r\n"
			+ "from empleado e\r\n"
			+ "inner join perfil_spring ps on ps.id = e.id_perfil_spring\r\n"
			+ "inner join perfil_spring_app psa on psa.id_perfil_spring = ps.id\r\n"
			+ "inner join perfil_app pa on pa.id = psa.id_perfil_app\r\n"
			+ "inner join funcionalidad_acceso fa on fa.id_perfil_app = pa.id\r\n"
			+ "inner join funcionalidad_app fa2 on fa2.id = fa.id_funcionalidad\r\n"
			+ "WHERE fa2.codigo  = ?1 and pa.codigo  = ?2 \r\n"
			+ "order by e.id asc", nativeQuery = true)
	List<Map<Integer, String>> listPublicacionPefilAprobador(String funcionalidadAppCod, String funcionalidadAccesoCod);

}
