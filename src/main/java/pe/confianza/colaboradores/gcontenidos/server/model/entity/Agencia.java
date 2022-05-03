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
@Table(name = "agencia")
public class Agencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = false)
	private String estado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idCorredor")
	private Corredor corredor;
	
	@Column(nullable = true)
	private String codigoCorredor;
	
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Corredor getCorredor() {
		return corredor;
	}

	public void setCorredor(Corredor corredor) {
		this.corredor = corredor;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getCodigoCorredor() {
		return codigoCorredor;
	}

	public void setCodigoCorredor(String codigoCorredor) {
		this.codigoCorredor = codigoCorredor;
	}
	
	
	
	

}
