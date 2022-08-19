package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.ArrayList;
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
	
	private boolean distribucionVariable;
	
	private boolean todasAgencias;
	
	private List<String> territoriosSeleccionados;
	
	private List<String> corredoresSeleccionandos;
	
	private List<String> agenciasSeleccionadas;
	
	@Min(value = 0, message = "Ingrese código de frecuencia de distribución monto válido")
	private int codigoFrecuenciaDistribucion;
	
	public RequestDistribucionConcepto() {
		this.territoriosSeleccionados = new ArrayList<>();
		this.corredoresSeleccionandos = new ArrayList<>();
		this.agenciasSeleccionadas = new ArrayList<>();
	}

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

	public boolean isDistribucionVariable() {
		return distribucionVariable;
	}

	public void setDistribucionVariable(boolean distribucionVariable) {
		this.distribucionVariable = distribucionVariable;
	}

	public boolean isTodasAgencias() {
		return todasAgencias;
	}

	public void setTodasAgencias(boolean todasAgencias) {
		this.todasAgencias = todasAgencias;
	}

	public List<String> getTerritoriosSeleccionados() {
		return territoriosSeleccionados;
	}

	public void setTerritoriosSeleccionados(List<String> territoriosSeleccionados) {
		this.territoriosSeleccionados = territoriosSeleccionados;
	}

	public List<String> getCorredoresSeleccionandos() {
		return corredoresSeleccionandos;
	}

	public void setCorredoresSeleccionandos(List<String> corredoresSeleccionandos) {
		this.corredoresSeleccionandos = corredoresSeleccionandos;
	}

	public List<String> getAgenciasSeleccionadas() {
		return agenciasSeleccionadas;
	}

	public void setAgenciasSeleccionadas(List<String> agenciasSeleccionadas) {
		this.agenciasSeleccionadas = agenciasSeleccionadas;
	}

	public int getCodigoFrecuenciaDistribucion() {
		return codigoFrecuenciaDistribucion;
	}

	public void setCodigoFrecuenciaDistribucion(int codigoFrecuenciaDistribucion) {
		this.codigoFrecuenciaDistribucion = codigoFrecuenciaDistribucion;
	}

	

}
