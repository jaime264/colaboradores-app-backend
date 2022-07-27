package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.migracion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imp_corredor")
public class MigracionCorredor extends MigracionBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false,  length = 5)
	private String sucursalGrupo;
	
	@Column(nullable = false, length = 50)
	private String descripcion;
	
	private Long codigoRepresentante;
	
	@Column(nullable = false, length = 4)
	private String codigoTerritorio;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSucursalGrupo() {
		return sucursalGrupo;
	}

	public void setSucursalGrupo(String sucursalGrupo) {
		this.sucursalGrupo = sucursalGrupo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getCodigoRepresentante() {
		return codigoRepresentante;
	}

	public void setCodigoRepresentante(Long codigoRepresentante) {
		this.codigoRepresentante = codigoRepresentante;
	}

	public String getCodigoTerritorio() {
		return codigoTerritorio;
	}

	public void setCodigoTerritorio(String codigoTerritorio) {
		this.codigoTerritorio = codigoTerritorio;
	}

	
	
	

}
