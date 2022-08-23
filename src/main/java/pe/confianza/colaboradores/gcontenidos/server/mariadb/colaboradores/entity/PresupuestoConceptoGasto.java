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

import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoFrecuencia;
import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoTipo;

@Entity
@Table(name = "presupuesto_concepto_gasto")
public class PresupuestoConceptoGasto extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "id_tipo_gasto_presupuesto")
	private PresupuestoTipoGasto presupuestoTipoGasto;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_glg_asignado")
	private GastoGlgAsignado glgAsignado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_concepto_detalle")
	private GastoConceptoDetalle conceptoDetalle;
	
	private boolean distribuido;
	
	private double presupuestoAsignado;
	
	private double presupuestoConsumido;
	
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
	
	@OneToMany(mappedBy = "presupuestoConcepto", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PresupuestoAgenciaGasto> presupuestosAgencia;

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

	public PresupuestoTipoGasto getPresupuestoTipoGasto() {
		return presupuestoTipoGasto;
	}

	public void setPresupuestoTipoGasto(PresupuestoTipoGasto presupuestoTipoGasto) {
		this.presupuestoTipoGasto = presupuestoTipoGasto;
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

	public boolean isDistribuido() {
		return distribuido;
	}

	public void setDistribuido(boolean distribuido) {
		this.distribuido = distribuido;
	}

	public double getPresupuestoAsignado() {
		return presupuestoAsignado;
	}

	public void setPresupuestoAsignado(double presupuestoAsignado) {
		this.presupuestoAsignado = presupuestoAsignado;
	}

	public double getPresupuestoConsumido() {
		return presupuestoConsumido;
	}

	public void setPresupuestoConsumido(double presupuestoConsumido) {
		this.presupuestoConsumido = presupuestoConsumido;
	}

	public Boolean getDistribucionVariable() {
		return distribucionVariable;
	}

	public void setDistribucionVariable(Boolean distribucionVariable) {
		this.distribucionVariable = distribucionVariable;
	}

	public Boolean getDistribucionUniforme() {
		return distribucionUniforme;
	}

	public void setDistribucionUniforme(Boolean distribucionUniforme) {
		this.distribucionUniforme = distribucionUniforme;
	}

	public Integer getIdTipoDistribucionMonto() {
		return idTipoDistribucionMonto;
	}

	public void setIdTipoDistribucionMonto(Integer idTipoDistribucionMonto) {
		this.idTipoDistribucionMonto = idTipoDistribucionMonto;
	}

	public String getDescripcionDistribucionMonto() {
		return descripcionDistribucionMonto;
	}

	public void setDescripcionDistribucionMonto(String descripcionDistribucionMonto) {
		this.descripcionDistribucionMonto = descripcionDistribucionMonto;
	}

	public Integer getIdFrecuenciaDistribucion() {
		return idFrecuenciaDistribucion;
	}

	public void setIdFrecuenciaDistribucion(Integer idFrecuenciaDistribucion) {
		this.idFrecuenciaDistribucion = idFrecuenciaDistribucion;
	}

	public String getDescripcionFrecuenciaDistribucion() {
		return descripcionFrecuenciaDistribucion;
	}

	public void setDescripcionFrecuenciaDistribucion(String descripcionFrecuenciaDistribucion) {
		this.descripcionFrecuenciaDistribucion = descripcionFrecuenciaDistribucion;
	}

	public List<PresupuestoAgenciaGasto> getPresupuestosAgencia() {
		return presupuestosAgencia;
	}

	public void setPresupuestosAgencia(List<PresupuestoAgenciaGasto> presupuestosAgencia) {
		this.presupuestosAgencia = presupuestosAgencia;
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
