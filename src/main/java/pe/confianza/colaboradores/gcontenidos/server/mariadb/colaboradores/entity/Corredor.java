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
@Table(name = "corredor")
public class Corredor extends EntidadAuditoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = false)
	private String estado;
	
	@Column(nullable = false)
	private String codigoTerritorio;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idTerritorio")
	private Territorio territorio;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idEmpleadoRepresentante")
	private Empleado representante;
	
	@Column(nullable = true)
	private Long codigoRepresentante;
	
	
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

	public Territorio getTerritorio() {
		return territorio;
	}

	public void setTerritorio(Territorio territorio) {
		this.territorio = territorio;
	}

	public Empleado getRepresentante() {
		return representante;
	}

	public void setRepresentante(Empleado representante) {
		this.representante = representante;
	}


	public String getCodigoTerritorio() {
		return codigoTerritorio;
	}

	public void setCodigoTerritorio(String codigoTerritorio) {
		this.codigoTerritorio = codigoTerritorio;
	}

	public Long getCodigoRepresentante() {
		return codigoRepresentante;
	}

	public void setCodigoRepresentante(Long codigoRepresentante) {
		this.codigoRepresentante = codigoRepresentante;
	}
	
	

}
