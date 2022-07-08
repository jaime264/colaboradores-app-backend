package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT id, "
		+ "codigo, "
		+ "apellido_paterno, "
		+ "apellido_materno, "
		+ "nombres, "
		+ "email, "
		+ "usuariobt, "
		+ "descripcion, "
		+ "cantidad_subordinados "
		+ "FROM aprobador_vacaciones_segundo_nivel")
public class VacacionAprobadorNivelII {
	
	@Id
	private Long id;
	private String codigo;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private String email;
	private String usuariobt;
	private String descripcion;
	private Long cantidadSubordinados;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsuariobt() {
		return usuariobt;
	}
	public void setUsuariobt(String usuariobt) {
		this.usuariobt = usuariobt;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getCantidadSubordinados() {
		return cantidadSubordinados;
	}
	public void setCantidadSubordinados(Long cantidadSubordinados) {
		this.cantidadSubordinados = cantidadSubordinados;
	}
	
	
	
	
	

}
