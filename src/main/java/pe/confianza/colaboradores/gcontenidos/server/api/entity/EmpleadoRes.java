package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;

public class EmpleadoRes {

	private Long idEmpleado;
	private String nombres;
	private String apeMaterno;
	private String apePaterno;
	private Long idCargo;
	private String descCargo;
	private String idSucursal;	
	private String nomSucursal;
	private Long fechaNac;	
	private Long fechaIng;
	private String email;
	private String celular;
	private String direccion;
	private String sexo;

	private String usuarioBT;
	private Long ultimaPublicacion;
	private LogAuditoria logAuditoria;
	
	private Long fechaFinContrato;
	private Long codigoUnidadNegocio;
	private Long codigoJefeInmediato;
	private Long codigoNivel1;
	private Long codigoNivel2;
	private boolean bloqueoVacaciones;
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApeMaterno() {
		return apeMaterno;
	}
	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}
	public String getApePaterno() {
		return apePaterno;
	}
	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}
	public Long getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(Long idCargo) {
		this.idCargo = idCargo;
	}
	public String getDescCargo() {
		return descCargo;
	}
	public void setDescCargo(String descCargo) {
		this.descCargo = descCargo;
	}
	public String getIdSucursal() {
		return idSucursal;
	}
	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}
	public String getNomSucursal() {
		return nomSucursal;
	}
	public void setNomSucursal(String nomSucursal) {
		this.nomSucursal = nomSucursal;
	}
	public Long getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(Long fechaNac) {
		this.fechaNac = fechaNac;
	}
	public Long getFechaIng() {
		return fechaIng;
	}
	public void setFechaIng(Long fechaIng) {
		this.fechaIng = fechaIng;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getUsuarioBT() {
		return usuarioBT;
	}
	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}
	public Long getUltimaPublicacion() {
		return ultimaPublicacion;
	}
	public void setUltimaPublicacion(Long ultimaPublicacion) {
		this.ultimaPublicacion = ultimaPublicacion;
	}
	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}
	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}
	public Long getFechaFinContrato() {
		return fechaFinContrato;
	}
	public void setFechaFinContrato(Long fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}
	public Long getCodigoUnidadNegocio() {
		return codigoUnidadNegocio;
	}
	public void setCodigoUnidadNegocio(Long codigoUnidadNegocio) {
		this.codigoUnidadNegocio = codigoUnidadNegocio;
	}
	public Long getCodigoJefeInmediato() {
		return codigoJefeInmediato;
	}
	public void setCodigoJefeInmediato(Long codigoJefeInmediato) {
		this.codigoJefeInmediato = codigoJefeInmediato;
	}
	
	public Long getCodigoNivel1() {
		return codigoNivel1;
	}
	public void setCodigoNivel1(Long codigoNivel1) {
		this.codigoNivel1 = codigoNivel1;
	}
	public Long getCodigoNivel2() {
		return codigoNivel2;
	}
	public void setCodigoNivel2(Long codigoNivel2) {
		this.codigoNivel2 = codigoNivel2;
	}
	public boolean isBloqueoVacaciones() {
		return bloqueoVacaciones;
	}
	public void setBloqueoVacaciones(boolean bloqueoVacaciones) {
		this.bloqueoVacaciones = bloqueoVacaciones;
	}
	@Override
	public String toString() {
		return "EmpleadoRes [idEmpleado=" + idEmpleado + ", nombres=" + nombres + ", apeMaterno=" + apeMaterno
				+ ", apePaterno=" + apePaterno + ", idCargo=" + idCargo + ", descCargo=" + descCargo + ", idSucursal="
				+ idSucursal + ", nomSucursal=" + nomSucursal + ", fechaNac=" + fechaNac + ", fechaIng=" + fechaIng
				+ ", email=" + email + ", celular=" + celular + ", direccion=" + direccion + ", sexo=" + sexo
				+ ", usuarioBT=" + usuarioBT + ", ultimaPublicacion=" + ultimaPublicacion + ", logAuditoria="
				+ logAuditoria + ", fechaFinContrato=" + fechaFinContrato + ", codigoUnidadNegocio="
				+ codigoUnidadNegocio + ", codigoJefeInmediato=" + codigoJefeInmediato + ", codigoNIvel1="
				+ codigoNivel1 + ", codigoNivel2=" + codigoNivel2 + ", bloqueoVacaciones=" + bloqueoVacaciones + "]";
	}
	
	
	
}
