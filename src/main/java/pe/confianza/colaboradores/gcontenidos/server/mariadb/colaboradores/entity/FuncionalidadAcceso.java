package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "funcionalidad_acceso")
public class FuncionalidadAcceso extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idPerfilApp")
	private PerfilApp perfilApp;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idFuncionalidad")
	private FuncionalidadApp funcionalidad;
	
	private boolean modificar;
	
	private boolean nuevo;
	
	private boolean eliminar;
	
	private boolean consultar;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PerfilApp getPerfilApp() {
		return perfilApp;
	}

	public void setPerfilApp(PerfilApp perfilApp) {
		this.perfilApp = perfilApp;
	}

	public FuncionalidadApp getFuncionalidad() {
		return funcionalidad;
	}

	public void setFuncionalidad(FuncionalidadApp funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public boolean isEliminar() {
		return eliminar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public boolean isConsultar() {
		return consultar;
	}

	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}
	
	
	
	
}
