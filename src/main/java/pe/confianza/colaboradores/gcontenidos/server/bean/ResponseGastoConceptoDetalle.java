package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponseGastoConceptoDetalle {
	
	private long codigo;
	
	private String descripcion;
	
	private String conceptoPresupuestal;
	
	private ResponseGastoConcepto concepto;

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

	public String getConceptoPresupuestal() {
		return conceptoPresupuestal;
	}

	public void setConceptoPresupuestal(String conceptoPresupuestal) {
		this.conceptoPresupuestal = conceptoPresupuestal;
	}

	public ResponseGastoConcepto getConcepto() {
		return concepto;
	}

	public void setConcepto(ResponseGastoConcepto concepto) {
		this.concepto = concepto;
	}
	
	

}
