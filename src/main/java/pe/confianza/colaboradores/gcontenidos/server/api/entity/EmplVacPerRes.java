package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import java.util.List;

public class EmplVacPerRes {

	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String puesto;
	private Long idEmpleado;
	private String usuarioBt;
	private List<VacacionPeriodo> vacacionPeriodo;

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getUsuarioBt() {
		return usuarioBt;
	}

	public void setUsuarioBt(String usuarioBt) {
		this.usuarioBt = usuarioBt;
	}

	public List<VacacionPeriodo> getVacacionPeriodo() {
		return vacacionPeriodo;
	}

	public void setVacacionPeriodo(List<VacacionPeriodo> vacacionPeriodo) {
		this.vacacionPeriodo = vacacionPeriodo;
	}

}
