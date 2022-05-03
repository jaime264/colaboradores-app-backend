package pe.confianza.colaboradores.gcontenidos.server.model.entity;

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
@Table(name = "unidad_operativa")
public class UnidadOperativa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = true)
	private String codigoResponsable;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idEmpleadoResponsable")
	private Empleado responsable;
	
	@Column(nullable = true)
	private String codigoUnidadOperativaSuperior;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idAgencia")
	private Agencia agencia;
	
	@Column(nullable = true)
	private String codigoAgencia;
	
	@Column(nullable = false)
	private String estado;
	
	@Column(nullable = true)
	private String def;
	
	@Column(columnDefinition = "TIMESTAMP" )
	private LocalDateTime fechaActualizacion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoResponsable() {
		return codigoResponsable;
	}

	public void setCodigoResponsable(String codigoResponsable) {
		this.codigoResponsable = codigoResponsable;
	}

	public Empleado getResponsable() {
		return responsable;
	}

	public void setResponsable(Empleado responsable) {
		this.responsable = responsable;
	}

	public String getCodigoUnidadOperativaSuperior() {
		return codigoUnidadOperativaSuperior;
	}

	public void setCodigoUnidadOperativaSuperior(String codigoUnidadOperativaSuperior) {
		this.codigoUnidadOperativaSuperior = codigoUnidadOperativaSuperior;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	
	

}
