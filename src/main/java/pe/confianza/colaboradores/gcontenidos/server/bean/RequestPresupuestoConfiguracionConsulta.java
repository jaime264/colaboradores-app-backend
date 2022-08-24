package pe.confianza.colaboradores.gcontenidos.server.bean;

public class RequestPresupuestoConfiguracionConsulta  extends RequestAuditoriaBase {

	private long codigoPresupuestoConcepto;

	public long getCodigoPresupuestoConcepto() {
		return codigoPresupuestoConcepto;
	}

	public void setCodigoPresupuestoConcepto(long codigoPresupuestoConcepto) {
		this.codigoPresupuestoConcepto = codigoPresupuestoConcepto;
	}
	
}
