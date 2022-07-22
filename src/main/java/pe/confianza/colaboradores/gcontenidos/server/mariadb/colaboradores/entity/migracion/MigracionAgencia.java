package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.migracion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "migracion_agencia")
public class MigracionAgencia extends MigracionBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = true)
	private String codigoOficinaMatriz;
	
	private String codigoCorredor;

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

	public String getCodigoOficinaMatriz() {
		return codigoOficinaMatriz;
	}

	public void setCodigoOficinaMatriz(String codigoOficinaMatriz) {
		this.codigoOficinaMatriz = codigoOficinaMatriz;
	}

	public String getCodigoCorredor() {
		return codigoCorredor;
	}

	public void setCodigoCorredor(String codigoCorredor) {
		this.codigoCorredor = codigoCorredor;
	}
	
	
}
