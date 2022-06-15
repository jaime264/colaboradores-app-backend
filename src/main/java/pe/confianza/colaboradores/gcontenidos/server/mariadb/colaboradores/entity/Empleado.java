package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "empleado")
public class Empleado extends EntidadAuditoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = true)
	private Long codigo;
	
	@Column(nullable = false)
	private String nombres;
	
	@Column(nullable = false)
	private String apellidoPaterno;
	
	@Column(nullable = false)
	private String apellidoMaterno;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idPuesto")
	private Puesto puesto;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idAgencia")
	private Agencia agencia;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaNacimiento;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaIngreso;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaFinContrato;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = true)
	private String celular;
	
	@Column(nullable = true)
	private String direccion;
	
	@Column(nullable = false)
	private String sexo;
	
	@Column(nullable = false)
	private String usuarioBT;
	
	@Column(nullable = true)
	private Long codigoUnidadNegocio;
	
	@Column(nullable = true)
	private Long codigoJefeInmediato;
	
	@Column(nullable = true)
	private Long codigoNivel1;
	
	@Column(nullable = true)
	private Long codigoNivel2;
	
	@Column(nullable = true)
	private boolean bloqueoVacaciones;
	
	private boolean aceptaTerminosCondiciones;
	
	private LocalDateTime fechaAceptacionTc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

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

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public LocalDate getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(LocalDate fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
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

	public boolean isAceptaTerminosCondiciones() {
		return aceptaTerminosCondiciones;
	}

	public void setAceptaTerminosCondiciones(boolean aceptaTerminosCondiciones) {
		this.aceptaTerminosCondiciones = aceptaTerminosCondiciones;
	}
	
	

	public LocalDateTime getFechaAceptacionTc() {
		return fechaAceptacionTc;
	}

	public void setFechaAceptacionTc(LocalDateTime fechaAceptacionTc) {
		this.fechaAceptacionTc = fechaAceptacionTc;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", codigo=" + codigo + ", nombres=" + nombres + ", apellidoPaterno="
				+ apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", puesto=" + puesto + ", agencia="
				+ agencia + ", fechaNacimiento=" + fechaNacimiento + ", fechaIngreso=" + fechaIngreso
				+ ", fechaFinContrato=" + fechaFinContrato + ", email=" + email + ", celular=" + celular
				+ ", direccion=" + direccion + ", sexo=" + sexo + ", usuarioBT=" + usuarioBT + ", codigoUnidadNegocio=" + codigoUnidadNegocio + ", codigoJefeInmediato="
				+ codigoJefeInmediato + ", codigoNivel1=" + codigoNivel1 + ", codigoNivel2=" + codigoNivel2
				+ ", bloqueoVacaciones=" + bloqueoVacaciones + "]";
	}

}
