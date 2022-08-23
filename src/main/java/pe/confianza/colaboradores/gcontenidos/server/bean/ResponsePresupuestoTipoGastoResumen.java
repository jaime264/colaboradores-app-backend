package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.ArrayList;
import java.util.List;

public class ResponsePresupuestoTipoGastoResumen {
	
	private String descripcionPresupuestoGeneral;
	private double presupuestoAsignado;
	private double presupuestoConsumido;
	
	private List<ResponsePresupuestoConceptoGasto> presupuestosConcepto;

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

	public List<ResponsePresupuestoConceptoGasto> getPresupuestosConcepto() {
		return presupuestosConcepto;
	}

	public void setPresupuestosConcepto(List<ResponsePresupuestoConceptoGasto> presupuestosConcepto) {
		this.presupuestosConcepto = presupuestosConcepto;
	}
	
		

}
