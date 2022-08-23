package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "gasto_concepto_detalle")
public class GastoConceptoDetalle extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@Column(nullable = false)
	private Long codigoSpring;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String descripcion;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String conceptoPresupuestal;
	
	@Column(nullable = false)
	private Long codigoConcepto;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(nullable = true, name = "id_concepto")
	private GastoConcepto concepto;
	
	private String cuentaContable;

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

	public GastoConcepto getConcepto() {
		return concepto;
	}

	public void setConcepto(GastoConcepto concepto) {
		this.concepto = concepto;
	}

	public String getConceptoPresupuestal() {
		return conceptoPresupuestal;
	}

	public void setConceptoPresupuestal(String conceptoPresupuestal) {
		this.conceptoPresupuestal = conceptoPresupuestal;
	}

	public Long getCodigoConcepto() {
		return codigoConcepto;
	}

	public void setCodigoConcepto(Long codigoConcepto) {
		this.codigoConcepto = codigoConcepto;
	}

	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	
	

}
