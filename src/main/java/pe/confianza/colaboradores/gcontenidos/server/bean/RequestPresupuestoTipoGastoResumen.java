package pe.confianza.colaboradores.gcontenidos.server.bean;

public class RequestPresupuestoTipoGastoResumen extends RequestAuditoriaBase {

	private long codigoPresupuesto;
	private String codigoTipoGasto;

	public long getCodigoPresupuesto() {
		return codigoPresupuesto;
	}

	public void setCodigoPresupuesto(long codigoPresupuesto) {
		this.codigoPresupuesto = codigoPresupuesto;
	}

	public String getCodigoTipoGasto() {
		return codigoTipoGasto;
	}

	public void setCodigoTipoGasto(String codigoTipoGasto) {
		this.codigoTipoGasto = codigoTipoGasto;
	}
	
	
}
