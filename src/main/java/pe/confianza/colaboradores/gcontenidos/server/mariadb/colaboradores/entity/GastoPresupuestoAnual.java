package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gasto_presupuesto_anual")
public class GastoPresupuestoAnual extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@Column(nullable = false, length = 250)
	private String descripcion;
	
	private boolean activo;
	
	@OneToMany(mappedBy = "presupuestoAnual", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<GastoPresupuestoDistribucionConcepto> distribucionesConcepto;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<GastoPresupuestoDistribucionConcepto> getDistribucionesConcepto() {
		return distribucionesConcepto;
	}

	public void setDistribucionesConcepto(List<GastoPresupuestoDistribucionConcepto> distribucionesConcepto) {
		this.distribucionesConcepto = distribucionesConcepto;
	}
	

	
	
}
