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
	
	private boolean registrar;
	
	private boolean eliminar;
	
	private boolean consultar;
	
	private boolean aprobar;

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

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
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

	public boolean isAprobar() {
		return aprobar;
	}

	public void setAprobar(boolean aprobar) {
		this.aprobar = aprobar;
	}
	

	
	
	
	
	
}
