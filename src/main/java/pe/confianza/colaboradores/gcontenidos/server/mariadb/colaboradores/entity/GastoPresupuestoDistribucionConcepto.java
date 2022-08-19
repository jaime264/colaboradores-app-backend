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

import com.fasterxml.jackson.annotation.JsonIgnore;

import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoFrecuencia;
import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoTipo;

@Entity
@Table(name = "gasto_presupuesto_concepto")
public class GastoPresupuestoDistribucionConcepto  extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_glg_asignado")
	private GastoGlgAsignado glgAsignado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_concepto_detalle")
	private GastoConceptoDetalle conceptoDetalle;
	
	@Column(nullable = false)
	private double presupuesto;
	
	@Column(nullable = true)
	private String cuentaContable;
	
	private boolean distribuido;
	
	@Column(nullable = true)
	private Boolean distribucionVariable;
	
	@Column(nullable = true)
	private Boolean distribucionUniforme;
	
	@Column(nullable = true)
	private Integer idTipoDistribucionMonto;
	
	@Column(nullable = true)
	private String descripcionDistribucionMonto;
	
	@Column(nullable = true)
	private Integer idFrecuenciaDistribucion;
	
	@Column(nullable = true)
	private String descripcionFrecuenciaDistribucion;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_presupuesto_anual")
	private GastoPresupuestoAnual presupuestoAnual;
	
	@OneToMany(mappedBy = "distribucionConcepto", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<GastoPresupuestoDistribucionConceptoAgencia> distribucionesAgencia;
	
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

	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public boolean isDistribuido() {
		return distribuido;
	}

	public void setDistribuido(boolean distribuido) {
		this.distribuido = distribuido;
	}

	public boolean isDistribucionVariable() {
		return distribucionVariable;
	}

	public void setDistribucionVariable(boolean distribucionVariable) {
		this.distribucionVariable = distribucionVariable;
	}

	public boolean isDistribucionUniforme() {
		return distribucionUniforme;
	}

	public void setDistribucionUniforme(boolean distribucionUniforme) {
		this.distribucionUniforme = distribucionUniforme;
	}

	public int getIdTipoDistribucionMonto() {
		return idTipoDistribucionMonto;
	}

	public void setIdTipoDistribucionMonto(int idTipoDistribucionMonto) {
		this.idTipoDistribucionMonto = idTipoDistribucionMonto;
	}

	public String getDescripcionDistribucionMonto() {
		return descripcionDistribucionMonto;
	}

	public void setDescripcionDistribucionMonto(String descripcionDistribucionMonto) {
		this.descripcionDistribucionMonto = descripcionDistribucionMonto;
	}

	public int getIdFrecuenciaDistribucion() {
		return idFrecuenciaDistribucion;
	}

	public void setIdFrecuenciaDistribucion(int idFrecuenciaDistribucion) {
		this.idFrecuenciaDistribucion = idFrecuenciaDistribucion;
	}

	public String getDescripcionFrecuenciaDistribucion() {
		return descripcionFrecuenciaDistribucion;
	}

	public void setDescripcionFrecuenciaDistribucion(String descripcionFrecuenciaDistribucion) {
		this.descripcionFrecuenciaDistribucion = descripcionFrecuenciaDistribucion;
	}

	public GastoPresupuestoAnual getPresupuestoAnual() {
		return presupuestoAnual;
	}

	public void setPresupuestoAnual(GastoPresupuestoAnual presupuestoAnual) {
		this.presupuestoAnual = presupuestoAnual;
	}

	public List<GastoPresupuestoDistribucionConceptoAgencia> getDistribucionesAgencia() {
		return distribucionesAgencia;
	}

	public void setDistribucionesAgencia(List<GastoPresupuestoDistribucionConceptoAgencia> distribucionesAgencia) {
		this.distribucionesAgencia = distribucionesAgencia;
	}

	public void setTipoDistribucionMonto(DistribucionPresupuestoTipo tipo) {
		this.idTipoDistribucionMonto = tipo.codigo;
		this.descripcionDistribucionMonto = tipo.descripcion;
	}
	
	public DistribucionPresupuestoTipo tipoDistribucionMonto() {
		return DistribucionPresupuestoTipo.buscar(this.idTipoDistribucionMonto);
	}
	
	public void setFrecuenciaDistribucion(DistribucionPresupuestoFrecuencia frecuencia) {
		this.idFrecuenciaDistribucion = frecuencia.codigo;
		this.descripcionFrecuenciaDistribucion = frecuencia.descripcion;
	}
	
	public DistribucionPresupuestoFrecuencia frecuenciaDistribucion() {
		return DistribucionPresupuestoFrecuencia.buscar(this.idFrecuenciaDistribucion);
	}

}
