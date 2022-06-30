package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vacacion_programacion_estado")
public class VacacionProgramacionEstado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
}
