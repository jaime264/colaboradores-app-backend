package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vacacion_reprogramacion_contador")
public class VacacionReprogramacionContador extends EntidadAuditoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "idEmpleado")
	private Empleado empleado;
	
	private int anio;
	
	private int reprogramaciones;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getReprogramaciones() {
		return reprogramaciones;
	}

	public void setReprogramaciones(int reprogramaciones) {
		this.reprogramaciones = reprogramaciones;
	}
	
	

}
