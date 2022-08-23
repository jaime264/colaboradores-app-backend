package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.List;

public class ResponsePresupuestoConceptoGasto {
	
	private boolean puedeAdministrar;
	
	private long codigo;
	
	private ResponseGlgAsignado glgAsignado;
	
	private double presupuestoAsignado;
	
	private double presupuestoConsumido;
	
	private boolean distribuido;
	
	private ResponseGastoConceptoDetalle conceptoDetalle;
	
	private List<ResponsePresupuestoAgenciaGasto> agencias;
	
	private int solicitudes;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public ResponseGlgAsignado getGlgAsignado() {
		return glgAsignado;
	}

	public void setGlgAsignado(ResponseGlgAsignado glgAsignado) {
		this.glgAsignado = glgAsignado;
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

	public boolean isDistribuido() {
		return distribuido;
	}

	public void setDistribuido(boolean distribuido) {
		this.distribuido = distribuido;
	}

	public ResponseGastoConceptoDetalle getConceptoDetalle() {
		return conceptoDetalle;
	}

	public void setConceptoDetalle(ResponseGastoConceptoDetalle conceptoDetalle) {
		this.conceptoDetalle = conceptoDetalle;
	}

	public List<ResponsePresupuestoAgenciaGasto> getAgencias() {
		return agencias;
	}

	public void setAgencias(List<ResponsePresupuestoAgenciaGasto> agencias) {
		this.agencias = agencias;
	}

	public int getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(int solicitudes) {
		this.solicitudes = solicitudes;
	}

	public boolean isPuedeAdministrar() {
		return puedeAdministrar;
	}

	public void setPuedeAdministrar(boolean puedeAdministrar) {
		this.puedeAdministrar = puedeAdministrar;
	}
	
	

}
