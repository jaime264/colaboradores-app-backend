package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponseGastoPresupuestalAnual {
	
	private long codigo;
	private String descripcion;
	private boolean activo;
	private double presupuestoUsado;
	private double presupuesto;
	private int solicitudes;
	
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
	public double getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}
	public int getSolicitudes() {
		return solicitudes;
	}
	public void setSolicitudes(int solicitudes) {
		this.solicitudes = solicitudes;
	}
	
	

}
