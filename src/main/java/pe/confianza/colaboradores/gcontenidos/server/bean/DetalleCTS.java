package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;
import java.util.List;

public class DetalleCTS implements Serializable{

	private double indemnizacionAnual;
	private double tiempoValorizado;
	private double montoLocal;
	private double interes;
	private double retencionJudicial;
	private String tiempoCancelar;
	private double remuneracion;
	private Long idEmpleado;
	private String nombreCompleto;
	private List<ConceptoRemu> lstConceptoRemu;
	private Long fechaIni;
	private Long fechaFin;
	
	public double getIndemnizacionAnual() {
		return indemnizacionAnual;
	}
	public void setIndemnizacionAnual(double indemnizacionAnual) {
		this.indemnizacionAnual = indemnizacionAnual;
	}
	public double getTiempoValorizado() {
		return tiempoValorizado;
	}
	public void setTiempoValorizado(double tiempoValorizado) {
		this.tiempoValorizado = tiempoValorizado;
	}
	public double getMontoLocal() {
		return montoLocal;
	}
	public void setMontoLocal(double montoLocal) {
		this.montoLocal = montoLocal;
	}
	public double getInteres() {
		return interes;
	}
	public void setInteres(double interes) {
		this.interes = interes;
	}
	public double getRetencionJudicial() {
		return retencionJudicial;
	}
	public void setRetencionJudicial(double retencionJudicial) {
		this.retencionJudicial = retencionJudicial;
	}
	public String getTiempoCancelar() {
		return tiempoCancelar;
	}
	public void setTiempoCancelar(String tiempoCancelar) {
		this.tiempoCancelar = tiempoCancelar;
	}
	public double getRemuneracion() {
		return remuneracion;
	}
	public void setRemuneracion(double remuneracion) {
		this.remuneracion = remuneracion;
	}
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public List<ConceptoRemu> getLstConceptoRemu() {
		return lstConceptoRemu;
	}
	public void setLstConceptoRemu(List<ConceptoRemu> lstConceptoRemu) {
		this.lstConceptoRemu = lstConceptoRemu;
	}
	
	public Long getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(Long fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Long getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8689912011607086919L;

}
