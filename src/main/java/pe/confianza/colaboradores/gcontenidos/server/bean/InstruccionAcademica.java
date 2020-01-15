package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class InstruccionAcademica implements Serializable{
	
	private Long idEmpleado;
	private String idNivelAcademico;
	private String descNivelAcademico;
	private Long idCentroEstudio;
	private String descCentroEstudio;
	private String idCarrera;
	private String desccarrera;
	private String ultimoGrado;
	private Long secuencia;
	private Long idProfesion;
	private String descProfesion;
	private String fechaDesde;
	private String fechaHasta;
	private String paraPracticas;
	private String observaciones;
	
	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getIdNivelAcademico() {
		return idNivelAcademico;
	}

	public void setIdNivelAcademico(String idNivelAcademico) {
		this.idNivelAcademico = idNivelAcademico;
	}

	public String getDescNivelAcademico() {
		return descNivelAcademico;
	}

	public void setDescNivelAcademico(String descNivelAcademico) {
		this.descNivelAcademico = descNivelAcademico;
	}

	public Long getIdCentroEstudio() {
		return idCentroEstudio;
	}

	public void setIdCentroEstudio(Long idCentroEstudio) {
		this.idCentroEstudio = idCentroEstudio;
	}

	public String getDescCentroEstudio() {
		return descCentroEstudio;
	}

	public void setDescCentroEstudio(String descCentroEstudio) {
		this.descCentroEstudio = descCentroEstudio;
	}

	public String getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(String idCarrera) {
		this.idCarrera = idCarrera;
	}

	public String getDesccarrera() {
		return desccarrera;
	}

	public void setDesccarrera(String desccarrera) {
		this.desccarrera = desccarrera;
	}

	public String getUltimoGrado() {
		return ultimoGrado;
	}

	public void setUltimoGrado(String ultimoGrado) {
		this.ultimoGrado = ultimoGrado;
	}

	public Long getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}

	public Long getIdProfesion() {
		return idProfesion;
	}

	public void setIdProfesion(Long idProfesion) {
		this.idProfesion = idProfesion;
	}

	public String getDescProfesion() {
		return descProfesion;
	}

	public void setDescProfesion(String descProfesion) {
		this.descProfesion = descProfesion;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getParaPracticas() {
		return paraPracticas;
	}

	public void setParaPracticas(String paraPracticas) {
		this.paraPracticas = paraPracticas;
	}	
	
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
