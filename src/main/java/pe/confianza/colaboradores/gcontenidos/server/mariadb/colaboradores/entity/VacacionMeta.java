package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vacacion_meta")
public class VacacionMeta extends EntidadAuditoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "idEmpleado")
	private Empleado empleado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idPeriodoTrunco")
	private PeriodoVacacion periodoTrunco;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idPeriodoVencido")
	private PeriodoVacacion periodoVencido;
	
	private double metaInicial;
	
	private double meta;
	
	private double diasVencidos;
	
	private double diasTruncos;
	
	private int anio;
	
	private LocalDate fechaCorte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public PeriodoVacacion getPeriodoTrunco() {
		return periodoTrunco;
	}

	public void setPeriodoTrunco(PeriodoVacacion periodoTrunco) {
		this.periodoTrunco = periodoTrunco;
	}

	public PeriodoVacacion getPeriodoVencido() {
		return periodoVencido;
	}

	public void setPeriodoVencido(PeriodoVacacion periodoVencido) {
		this.periodoVencido = periodoVencido;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}

	public double getDiasVencidos() {
		return diasVencidos;
	}

	public void setDiasVencidos(double diasVencidos) {
		this.diasVencidos = diasVencidos;
	}

	public double getDiasTruncos() {
		return diasTruncos;
	}

	public void setDiasTruncos(double diasTruncos) {
		this.diasTruncos = diasTruncos;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public LocalDate getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(LocalDate fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public double getMetaInicial() {
		return metaInicial;
	}

	public void setMetaInicial(double metaInicial) {
		this.metaInicial = metaInicial;
	}


	
	
	
}
