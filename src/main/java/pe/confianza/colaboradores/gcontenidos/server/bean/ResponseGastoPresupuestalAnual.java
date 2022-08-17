package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponseGastoPresupuestalAnual {
	
	private long codigo;
	private String descripcion;
	private boolean activo;
	private double presupuestoUsado;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public double getPresupuestoUsado() {
		return presupuestoUsado;
	}
	public void setPresupuestoUsado(double presupuestoUsado) {
		this.presupuestoUsado = presupuestoUsado;
	}
	
	

}
