package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.migracion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imp_division")
public class MigracionDivision extends MigracionBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = true)
	private Integer codigo;
	
	@Column(nullable = true)
	private String descripcion;
	
	@Column(nullable = true)
	private int codigoSpring;
	
	@Column(nullable = true)
	private String persona;
	
	@Column(nullable = true)
	private Integer codigoCargo;
	
	@Column(nullable = true)
	private String descripcionCargo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCodigoSpring() {
		return codigoSpring;
	}

	public void setCodigoSpring(int codigoSpring) {
		this.codigoSpring = codigoSpring;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public Integer getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(Integer codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getDescripcionCargo() {
		return descripcionCargo;
	}

	public void setDescripcionCargo(String descripcionCargo) {
		this.descripcionCargo = descripcionCargo;
	}
	
	
	
}
