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
@Table(name = "presupuesto_agencia_gasto")
public class PresupuestoAgenciaGasto  extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_agencia")
	private Agencia agencia;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_presupuesto_concepto")
	private PresupuestoConceptoGasto presupuestoConcepto;
	
	private double presupuestoAsignado;
	
	private double presupuestoConsumido;
	
	@OneToMany(mappedBy = "presupuestoAgencia", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PresupuestoPeriodoGasto> presupuestoPeriodo;

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

	public PresupuestoConceptoGasto getPresupuestoConcepto() {
		return presupuestoConcepto;
	}

	public void setPresupuestoConcepto(PresupuestoConceptoGasto presupuestoConcepto) {
		this.presupuestoConcepto = presupuestoConcepto;
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

	public List<PresupuestoPeriodoGasto> getPresupuestoPeriodo() {
		return presupuestoPeriodo;
	}

	public void setPresupuestoPeriodo(List<PresupuestoPeriodoGasto> presupuestoPeriodo) {
		this.presupuestoPeriodo = presupuestoPeriodo;
	}
	
	
	
	

}
