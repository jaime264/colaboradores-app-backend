package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gasto_concepto")
public class GastoConcepto extends EntidadAuditoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long codigo;
	
	@Column(nullable = false)
	private Long codigoSpring;
	
	@Column(nullable = false, length = 80)
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idTipo")
	private GastoConceptoTipo tipo;
	
	@OneToMany(mappedBy = "concepto", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<GastoConceptoDetalle> detalles;

	public List<GastoConceptoDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<GastoConceptoDetalle> detalles) {
		this.detalles = detalles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoSpring() {
		return codigoSpring;
	}

	public void setCodigoSpring(Long codigoSpring) {
		this.codigoSpring = codigoSpring;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public GastoConceptoTipo getTipo() {
		return tipo;
	}

	public void setTipo(GastoConceptoTipo tipo) {
		this.tipo = tipo;
	}
	
	

}
