package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.ArrayList;
import java.util.List;

public class ResponsePresupuestoAnualDistribucionConcepto {
	
	private long codigo;
	
	private ResponseGlgAsignado glgAsignado;
	
	private double presupuesto;
	
	private String cuentaContable;
	
	private boolean distribuido;
	
	private List<ResponsePresupuestoAnualDistribucionConceptoAgencia> agencias;
	
	public ResponsePresupuestoAnualDistribucionConcepto() {
		this.agencias = new ArrayList<>();
	}

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

	public double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public List<ResponsePresupuestoAnualDistribucionConceptoAgencia> getAgencias() {
		return agencias;
	}

	public void setAgencias(List<ResponsePresupuestoAnualDistribucionConceptoAgencia> agencias) {
		this.agencias = agencias;
	}

	public boolean isDistribuido() {
		return distribuido;
	}

	public void setDistribuido(boolean distribuido) {
		this.distribuido = distribuido;
	}

	

	
	
	
	

}
