package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoFrecuencia;
import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoTipo;

@Entity
@Table(name = "presupuesto_concepto_distribucion_gasto")
public class PresupuestoConceptoDistribucionGasto extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@Column(nullable = false)
	private boolean configurado;
	
	@Column(nullable = true)
	private Boolean distribucionExcel;
	
	@Column(nullable = true)
	private Boolean districucionAutomatica;
	
	@Column(nullable = true)
	private Boolean noDistribuir;
	
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
	
	@Column(nullable = true)
	private Double montoDistribuir;
	
	@Lob
	@Column(nullable = true, columnDefinition = "LONGBLOB")
	private byte[] archivoExcel;
	
	@OneToOne(mappedBy = "distribucion")
	private PresupuestoConceptoGasto presupuestoConcepto;

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

	public boolean isConfigurado() {
		return configurado;
	}

	public void setConfigurado(boolean configurado) {
		this.configurado = configurado;
	}

	public Boolean getDistribucionExcel() {
		return distribucionExcel;
	}

	public void setDistribucionExcel(Boolean distribucionExcel) {
		this.distribucionExcel = distribucionExcel;
	}

	public Boolean getDistricucionAutomatica() {
		return districucionAutomatica;
	}

	public void setDistricucionAutomatica(Boolean districucionAutomatica) {
		this.districucionAutomatica = districucionAutomatica;
	}

	public Boolean getNoDistribuir() {
		return noDistribuir;
	}

	public void setNoDistribuir(Boolean noDistribuir) {
		this.noDistribuir = noDistribuir;
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

	public Double getMontoDistribuir() {
		return montoDistribuir;
	}

	public void setMontoDistribuir(Double montoDistribuir) {
		this.montoDistribuir = montoDistribuir;
	}

	public byte[] getArchivoExcel() {
		return archivoExcel;
	}

	public void setArchivoExcel(byte[] archivoExcel) {
		this.archivoExcel = archivoExcel;
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

	public PresupuestoConceptoGasto getPresupuestoConcepto() {
		return presupuestoConcepto;
	}

	public void setPresupuestoConcepto(PresupuestoConceptoGasto presupuestoConcepto) {
		this.presupuestoConcepto = presupuestoConcepto;
	}	
	
	

}
