package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.List;

import javax.validation.constraints.Min;

public class RequestDistribucionConcepto extends RequestAuditoriaBase {
	
	@Min(value = 0, message = "Ingrese un código de concepto válido")
	private long codigoConcepto;
	
	@Min(value = 0, message = "Ingrese tipo de distribución monto válido")
	private int tipoDistribucionMonto;
	
	@Min(value = 0, message = "Ingrese valor monto a distribuir válido")
	private double valorMontoDistribuir;
	
	private boolean distribucionExcel;
	
	private boolean distribucionUniforme;
	
	private boolean todasAgencias;
	
	private List<String> agenciasCodigo;
	
	@Min(value = 0, message = "Ingrese código de frecuencia de distribución monto válido")
	private int codigoFrecuenciaDistribucion;

	public long getCodigoConcepto() {
		return codigoConcepto;
	}

	public void setCodigoConcepto(long codigoConcepto) {
		this.codigoConcepto = codigoConcepto;
	}

	public int getTipoDistribucionMonto() {
		return tipoDistribucionMonto;
	}

	public void setTipoDistribucionMonto(int tipoDistribucionMonto) {
		this.tipoDistribucionMonto = tipoDistribucionMonto;
	}

	public double getValorMontoDistribuir() {
		return valorMontoDistribuir;
	}

	public void setValorMontoDistribuir(double valorMontoDistribuir) {
		this.valorMontoDistribuir = valorMontoDistribuir;
	}

	public boolean isDistribucionExcel() {
		return distribucionExcel;
	}

	public void setDistribucionExcel(boolean distribucionExcel) {
		this.distribucionExcel = distribucionExcel;
	}

	public boolean isDistribucionUniforme() {
		return distribucionUniforme;
	}

	public void setDistribucionUniforme(boolean distribucionUniforme) {
		this.distribucionUniforme = distribucionUniforme;
	}

	public boolean isTodasAgencias() {
		return todasAgencias;
	}

	public void setTodasAgencias(boolean todasAgencias) {
		this.todasAgencias = todasAgencias;
	}

	public List<String> getAgenciasCodigo() {
		return agenciasCodigo;
	}

	public void setAgenciasCodigo(List<String> agenciasCodigo) {
		this.agenciasCodigo = agenciasCodigo;
	}

	public int getCodigoFrecuenciaDistribucion() {
		return codigoFrecuenciaDistribucion;
	}

	public void setCodigoFrecuenciaDistribucion(int codigoFrecuenciaDistribucion) {
		this.codigoFrecuenciaDistribucion = codigoFrecuenciaDistribucion;
	}
	
	

	
	
	

}
