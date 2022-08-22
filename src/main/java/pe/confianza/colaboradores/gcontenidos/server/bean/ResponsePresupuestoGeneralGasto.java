package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponsePresupuestoGeneralGasto {
	
	private long codigo;
	private String descripcion;
	private boolean activo;
	private double presupuestoAsignado;
	private double presupuestoConsumido;
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
	public double getPresupuestoAsignado() {
		return presupuestoAsignado;
	}
	public void setPresupuestoAsignado(double presupuestoAsignado) {
		this.presupuestoAsignado = presupuestoAsignado;
	}
	public double getPresupuestoConsumido() {
		return presupuestoConsumido;
	}
	public void setPresupuestoConsumido(double presupuestoConsumido) {
		this.presupuestoConsumido = presupuestoConsumido;
	}
	public int getSolicitudes() {
		return solicitudes;
	}
	public void setSolicitudes(int solicitudes) {
		this.solicitudes = solicitudes;
	}
	

	
	

}
