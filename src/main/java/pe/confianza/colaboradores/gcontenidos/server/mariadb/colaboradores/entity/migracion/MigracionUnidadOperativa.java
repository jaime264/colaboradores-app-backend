package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.migracion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imp_unidad_operativa")
public class MigracionUnidadOperativa extends MigracionBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String descripcion; 
	
	@Column(nullable = true)
	private String codigoResponsable;
	
	@Column(nullable = true)
	private String codigoUnidadOperativaSuperior;
	
	@Column(nullable = true)
	private String codigoAgencia;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoResponsable() {
		return codigoResponsable;
	}

	public void setCodigoResponsable(String codigoResponsable) {
		this.codigoResponsable = codigoResponsable;
	}

	public String getCodigoUnidadOperativaSuperior() {
		return codigoUnidadOperativaSuperior;
	}

	public void setCodigoUnidadOperativaSuperior(String codigoUnidadOperativaSuperior) {
		this.codigoUnidadOperativaSuperior = codigoUnidadOperativaSuperior;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}
	
	

}
