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
@Table(name = "gasto_presupuesto_distribucion")
public class GastoPresupuestoDistribucion  extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_agencia")
	private Agencia agencia;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_glg_asignado")
	private GastoGlgAsignado glgAsignado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_concepto_detalle")
	private GastoConceptoDetalle conceptoDetalle;
	
	@Column(nullable = false)
	private double presupuesto;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_presupuesto_anual")
	private GastoPresupuestoAnual presupuestoAnual;
	
	

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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public GastoGlgAsignado getGlgAsignado() {
		return glgAsignado;
	}

	public void setGlgAsignado(GastoGlgAsignado glgAsignado) {
		this.glgAsignado = glgAsignado;
	}

	public GastoConceptoDetalle getConceptoDetalle() {
		return conceptoDetalle;
	}

	public void setConceptoDetalle(GastoConceptoDetalle conceptoDetalle) {
		this.conceptoDetalle = conceptoDetalle;
	}

	public double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public GastoPresupuestoAnual getPresupuestoAnual() {
		return presupuestoAnual;
	}

	public void setPresupuestoAnual(GastoPresupuestoAnual presupuestoAnual) {
		this.presupuestoAnual = presupuestoAnual;
	}	
	

}
