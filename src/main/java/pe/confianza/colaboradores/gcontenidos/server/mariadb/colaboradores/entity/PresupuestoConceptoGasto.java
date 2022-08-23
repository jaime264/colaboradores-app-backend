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
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "presupuesto_concepto_gasto")
public class PresupuestoConceptoGasto extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	private double presupuestoAsignado;
	
	private double presupuestoConsumido;
	
	private double presupuestoDistribuido;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "id_tipo_gasto_presupuesto")
	private PresupuestoTipoGasto presupuestoTipoGasto;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_glg_asignado")
	private GastoGlgAsignado glgAsignado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_concepto_detalle")
	private GastoConceptoDetalle conceptoDetalle;
	
	@OneToOne
	@JoinColumn(nullable = false, name = "id_distribucion")
	private PresupuestoConceptoDistribucionGasto distribucion;
	
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

	public PresupuestoConceptoDistribucionGasto getDistribucion() {
		return distribucion;
	}

	public void setDistribucion(PresupuestoConceptoDistribucionGasto distribucion) {
		this.distribucion = distribucion;
	}

	public List<PresupuestoAgenciaGasto> getPresupuestosAgencia() {
		return presupuestosAgencia;
	}

	public void setPresupuestosAgencia(List<PresupuestoAgenciaGasto> presupuestosAgencia) {
		this.presupuestosAgencia = presupuestosAgencia;
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

	public double getPresupuestoDistribuido() {
		return presupuestoDistribuido;
	}

	public void setPresupuestoDistribuido(double presupuestoDistribuido) {
		this.presupuestoDistribuido = presupuestoDistribuido;
	}

	
	
	

}
