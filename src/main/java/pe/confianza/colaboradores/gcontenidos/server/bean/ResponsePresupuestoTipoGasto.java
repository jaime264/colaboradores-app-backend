package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponsePresupuestoTipoGasto {
	
	private long codigo;
	private String descripcionPresupuestoGeneral;
	private double presupuestoAsignado;
	private double presupuestoConsumido;
	private int solicitudes;
	
	private ResponseTipoGasto tipoGasto;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcionPresupuestoGeneral() {
		return descripcionPresupuestoGeneral;
	}
	public void setDescripcionPresupuestoGeneral(String descripcionPresupuestoGeneral) {
		this.descripcionPresupuestoGeneral = descripcionPresupuestoGeneral;
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
	public ResponseTipoGasto getTipoGasto() {
		return tipoGasto;
	}
	public void setTipoGasto(ResponseTipoGasto tipoGasto) {
		this.tipoGasto = tipoGasto;
	}
	
	

	
	

}
