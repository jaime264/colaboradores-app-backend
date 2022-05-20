package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

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
@Table(name = "puesto")
public class Puesto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private long codigo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = true)
	private String codigoTipoCargo;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idTipoCargo")
	private TipoCargo tipo;
	
	@Column(nullable = true)
	private String codigoTipoCalificacion;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idTipoCalificacion")
	private TipoCalificacionCargo calificacion;
	
	@Column(nullable = false)
	private String estado;
	
	@Column(nullable = true)
	private Long codigoPuestoSuperior;
	
	@Column(columnDefinition = "TIMESTAMP" )
	private LocalDateTime fechaActualizacion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public TipoCargo getTipo() {
		return tipo;
	}

	public void setTipo(TipoCargo tipo) {
		this.tipo = tipo;
	}

	public TipoCalificacionCargo getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(TipoCalificacionCargo calificacion) {
		this.calificacion = calificacion;
	}

	public String getEstado() {
		return estado;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigoTipoCargo() {
		return codigoTipoCargo;
	}

	public void setCodigoTipoCargo(String codigoTipoCargo) {
		this.codigoTipoCargo = codigoTipoCargo;
	}

	public String getCodigoTipoCalificacion() {
		return codigoTipoCalificacion;
	}

	public void setCodigoTipoCalificacion(String codigoTipoCalificacion) {
		this.codigoTipoCalificacion = codigoTipoCalificacion;
	}

	public Long getCodigoPuestoSuperior() {
		return codigoPuestoSuperior;
	}

	public void setCodigoPuestoSuperior(Long codigoPuestoSuperior) {
		this.codigoPuestoSuperior = codigoPuestoSuperior;
	}	
	
	

}
