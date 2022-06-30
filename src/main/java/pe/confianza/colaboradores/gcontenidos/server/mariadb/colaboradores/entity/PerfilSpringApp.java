package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "perfil_spring_app")
public class PerfilSpringApp  extends EntidadAuditoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idPerfilSpring")
	private PerfilSpring perfilSpring;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idPerfilApp")
	private PerfilApp perfilApp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PerfilSpring getPerfilSpring() {
		return perfilSpring;
	}

	public void setPerfilSpring(PerfilSpring perfilSpring) {
		this.perfilSpring = perfilSpring;
	}

	public PerfilApp getPerfilApp() {
		return perfilApp;
	}

	public void setPerfilApp(PerfilApp perfilApp) {
		this.perfilApp = perfilApp;
	}
	
	

}
