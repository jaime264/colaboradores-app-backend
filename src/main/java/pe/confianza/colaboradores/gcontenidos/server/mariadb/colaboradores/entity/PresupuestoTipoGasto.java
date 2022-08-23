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
@Table(name = "presupuesto_tipo_gasto")
public class PresupuestoTipoGasto extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "id_tipo_gasto")
	private GastoConceptoTipo tipo;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "id_presupuesto_general")
	private PresupuestoGeneralGasto presupuestoGeneral;
	
	private double presupuestoAsignado;
	
	private double presupuestoConsumido;
	
	@OneToMany(mappedBy = "presupuestoTipoGasto", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PresupuestoConceptoGasto> presupuestosConcepto;

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

	public GastoConceptoTipo getTipo() {
		return tipo;
	}

	public void setTipo(GastoConceptoTipo tipo) {
		this.tipo = tipo;
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

	public PresupuestoGeneralGasto getPresupuestoGeneral() {
		return presupuestoGeneral;
	}

	public void setPresupuestoGeneral(PresupuestoGeneralGasto presupuestoGeneral) {
		this.presupuestoGeneral = presupuestoGeneral;
	}

	public List<PresupuestoConceptoGasto> getPresupuestosConcepto() {
		return presupuestosConcepto;
	}

	public void setPresupuestosConcepto(List<PresupuestoConceptoGasto> presupuestosConcepto) {
		this.presupuestosConcepto = presupuestosConcepto;
	}
	
	

}
