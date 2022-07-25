package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Territorio;

public interface TerritorioDao extends JpaRepository<Territorio, Long> {
	
}
