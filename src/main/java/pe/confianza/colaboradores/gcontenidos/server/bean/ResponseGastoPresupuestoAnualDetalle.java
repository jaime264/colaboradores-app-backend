package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.ArrayList;
import java.util.List;

public class ResponseGastoPresupuestoAnualDetalle {
	
	private ResponseGastoPresupuestalAnual presupuestoAnual;
	
	private List<ResponsePresupuestoAnualDistribucionConcepto> conceptos;
	
	public ResponseGastoPresupuestoAnualDetalle() {
		this.conceptos = new ArrayList<>();
	}

	public ResponseGastoPresupuestalAnual getPresupuestoAnual() {
		return presupuestoAnual;
	}

	public void setPresupuestoAnual(ResponseGastoPresupuestalAnual presupuestoAnual) {
		this.presupuestoAnual = presupuestoAnual;
	}

	public List<ResponsePresupuestoAnualDistribucionConcepto> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<ResponsePresupuestoAnualDistribucionConcepto> conceptos) {
		this.conceptos = conceptos;
	}


	

}
