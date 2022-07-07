package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponseAcceso {
	
	private String funcionalidadDescripcion;
	private String funcionalidadCodigo;
	private boolean consultar;
	private boolean registrar;
	private boolean aprobar;
	private boolean eliminar;
	private boolean modificar;
	private String perfilCodigo;
	private String perfilDescripcion;
	
	public String getFuncionalidadDescripcion() {
		return funcionalidadDescripcion;
	}
	public void setFuncionalidadDescripcion(String funcionalidadDescripcion) {
		this.funcionalidadDescripcion = funcionalidadDescripcion;
	}
	public String getFuncionalidadCodigo() {
		return funcionalidadCodigo;
	}
	public void setFuncionalidadCodigo(String funcionalidadCodigo) {
		this.funcionalidadCodigo = funcionalidadCodigo;
	}
	public boolean isConsultar() {
		return consultar;
	}
	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}
	public boolean isRegistrar() {
		return registrar;
	}
	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}
	public boolean isAprobar() {
		return aprobar;
	}
	public void setAprobar(boolean aprobar) {
		this.aprobar = aprobar;
	}
	public boolean isEliminar() {
		return eliminar;
	}
	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}
	public boolean isModificar() {
		return modificar;
	}
	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}
	public String getPerfilCodigo() {
		return perfilCodigo;
	}
	public void setPerfilCodigo(String perfilCodigo) {
		this.perfilCodigo = perfilCodigo;
	}
	public String getPerfilDescripcion() {
		return perfilDescripcion;
	}
	public void setPerfilDescripcion(String perfilDescripcion) {
		this.perfilDescripcion = perfilDescripcion;
	}
	
	

}
