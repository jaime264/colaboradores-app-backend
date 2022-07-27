package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "territorio")
public class Territorio extends EntidadAuditoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = false)
	private String estado;
	
	@Column(nullable = true)
	private String codigoRepresentante;
	
	@OneToMany(mappedBy = "territorio", fetch = FetchType.LAZY)
	@JsonIgnore
	@Transient
	private List<Corredor> corredores;

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

	public String getCodigoRepresentante() {
		return codigoRepresentante;
	}

	public void setCodigoRepresentante(String codigoRepresentante) {
		this.codigoRepresentante = codigoRepresentante;
	}
	
	

}
