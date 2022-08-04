package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "empleado")
public class Empleado extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@Column(columnDefinition = "DATE")
	private LocalDate fechaNacimiento;

	@Column(columnDefinition = "DATE")
	private LocalDate fechaIngreso;

	@Column(columnDefinition = "DATE")
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

	private String codigoPerfilSpring;
	
	@Column(nullable = true)
	private Long codigoGerenteDivision;

	@ManyToOne
	@JoinColumn(nullable = true, name = "idPerfilSpring")
	private PerfilSpring perfilSpring;

	@JsonIgnore
	@Transient
	private String corredor;

	@JsonIgnore
	@Transient
	private String territorio;

	public String getNombreCompleto() {
		return this.nombres + " " + this.apellidoPaterno + " " + this.apellidoMaterno;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", codigo=" + codigo + ", nombres=" + nombres + ", apellidoPaterno="
				+ apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", puesto=" + puesto + ", agencia="
				+ agencia + ", fechaNacimiento=" + fechaNacimiento + ", fechaIngreso=" + fechaIngreso
				+ ", fechaFinContrato=" + fechaFinContrato + ", email=" + email + ", celular=" + celular
				+ ", direccion=" + direccion + ", sexo=" + sexo + ", usuarioBT=" + usuarioBT + ", codigoUnidadNegocio="
				+ codigoUnidadNegocio + ", codigoJefeInmediato=" + codigoJefeInmediato + ", codigoNivel1="
				+ codigoNivel1 + ", codigoNivel2=" + codigoNivel2 + ", bloqueoVacaciones=" + bloqueoVacaciones + "]";
	}
	
	

}
