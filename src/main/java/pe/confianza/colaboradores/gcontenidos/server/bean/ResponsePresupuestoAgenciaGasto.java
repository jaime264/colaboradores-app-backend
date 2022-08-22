package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponsePresupuestoAgenciaGasto {
	
	private ResponseAgencia agencia;
	
	private double presupuestoAsignado;
	
	private double presupuestoConsumido;

	public ResponseAgencia getAgencia() {
		return agencia;
	}

	public void setAgencia(ResponseAgencia agencia) {
		this.agencia = agencia;
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

	
	

}
