package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT "
		+ "meta_id, empleado_id, empleado_usuariobt, empleado_nombres, empleado_apellido_paterno, empleado_nombre_completo, "
		+ "empleado_fecha_ingreso, puesto_id, puesto_descripcion, "
		+ "empleado_apellido_materno, empleado_email, aprobador_nivel1_id, "
		+ "aprobador_nivel1_usuariobt, aprobador_nivel1_nombres, aprobador_nivel1_apellido_paterno, "
		+ "aprobador_nivel1_apellido_materno, aprobador_nivel1_email, anio, "
		+ "meta_inicial, meta FROM vacacion_meta_resumen")
public class VacacionMetaResumen {
	
	@Id
	private Long metaId;
	private Long empleadoId;
	private String empleadoUsuariobt;
	private String empleadoNombres;
	private String empleadoApellidoPaterno;
	private String empleadoApellidoMaterno;
	private String empleadoNombreCompleto;
	private String empleadoEmail;
	private LocalDate empleadoFechaIngreso;
	private Long puestoId;
	private String puestoDescripcion;
	@Column(name = "aprobador_nivel1_id")
	private Long aprobadorNivel1Id;
	@Column(name = "aprobador_nivel1_usuariobt")
	private String aprobadorNivel1Usuariobt;
	@Column(name = "aprobador_nivel1_nombres")
	private String aprobadorNivel1Nombres;
	@Column(name = "aprobador_nivel1_apellido_paterno")
	private String aprobadorNivel1ApellidoPaterno;
	@Column(name = "aprobador_nivel1_apellido_materno")
	private String aprobadorNivel1ApellidoMaterno;
	@Column(name = "aprobador_nivel1_email")
	private String aprobadorNivel1Email;
	private Integer anio; 
	private Double metaInicial;
	private Double meta;
	
	public Long getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(Long empleadoId) {
		this.empleadoId = empleadoId;
	}
	public String getEmpleadoUsuariobt() {
		return empleadoUsuariobt;
	}
	public void setEmpleadoUsuariobt(String empleadoUsuariobt) {
		this.empleadoUsuariobt = empleadoUsuariobt;
	}
	public String getEmpleadoNombres() {
		return empleadoNombres;
	}
	public void setEmpleadoNombres(String empleadoNombres) {
		this.empleadoNombres = empleadoNombres;
	}
	public String getEmpleadoApellidoPaterno() {
		return empleadoApellidoPaterno;
	}
	public void setEmpleadoApellidoPaterno(String empleadoApellidoPaterno) {
		this.empleadoApellidoPaterno = empleadoApellidoPaterno;
	}
	public String getEmpleadoApellidoMaterno() {
		return empleadoApellidoMaterno;
	}
	public void setEmpleadoApellidoMaterno(String empleadoApellidoMaterno) {
		this.empleadoApellidoMaterno = empleadoApellidoMaterno;
	}
	public String getEmpleadoEmail() {
		return empleadoEmail;
	}
	public void setEmpleadoEmail(String empleadoEmail) {
		this.empleadoEmail = empleadoEmail;
	}
	public Long getAprobadorNivel1Id() {
		return aprobadorNivel1Id;
	}
	public void setAprobadorNivel1Id(Long aprobadorNivel1Id) {
		this.aprobadorNivel1Id = aprobadorNivel1Id;
	}
	public String getAprobadorNivel1Usuariobt() {
		return aprobadorNivel1Usuariobt;
	}
	public void setAprobadorNivel1Usuariobt(String aprobadorNivel1Usuariobt) {
		this.aprobadorNivel1Usuariobt = aprobadorNivel1Usuariobt;
	}
	public String getAprobadorNivel1Nombres() {
		return aprobadorNivel1Nombres;
	}
	public void setAprobadorNivel1Nombres(String aprobadorNivel1Nombres) {
		this.aprobadorNivel1Nombres = aprobadorNivel1Nombres;
	}
	public String getAprobadorNivel1ApellidoPaterno() {
		return aprobadorNivel1ApellidoPaterno;
	}
	public void setAprobadorNivel1ApellidoPaterno(String aprobadorNivel1ApellidoPaterno) {
		this.aprobadorNivel1ApellidoPaterno = aprobadorNivel1ApellidoPaterno;
	}
	public String getAprobadorNivel1ApellidoMaterno() {
		return aprobadorNivel1ApellidoMaterno;
	}
	public void setAprobadorNivel1ApellidoMaterno(String aprobadorNivel1ApellidoMaterno) {
		this.aprobadorNivel1ApellidoMaterno = aprobadorNivel1ApellidoMaterno;
	}
	public String getAprobadorNivel1Email() {
		return aprobadorNivel1Email;
	}
	public void setAprobadorNivel1Email(String aprobadorNivel1Email) {
		this.aprobadorNivel1Email = aprobadorNivel1Email;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public Double getMetaInicial() {
		return metaInicial;
	}
	public void setMetaInicial(Double metaInicial) {
		this.metaInicial = metaInicial;
	}
	public Double getMeta() {
		return meta;
	}
	public void setMeta(Double meta) {
		this.meta = meta;
	}
	public Long getMetaId() {
		return metaId;
	}
	public void setMetaId(Long metaId) {
		this.metaId = metaId;
	}
	public String getEmpleadoNombreCompleto() {
		return empleadoNombreCompleto;
	}
	public void setEmpleadoNombreCompleto(String empleadoNombreCompleto) {
		this.empleadoNombreCompleto = empleadoNombreCompleto;
	}
	public Long getPuestoId() {
		return puestoId;
	}
	public void setPuestoId(Long puestoId) {
		this.puestoId = puestoId;
	}
	public String getPuestoDescripcion() {
		return puestoDescripcion;
	}
	public void setPuestoDescripcion(String puestoDescripcion) {
		this.puestoDescripcion = puestoDescripcion;
	}
	public LocalDate getEmpleadoFechaIngreso() {
		return empleadoFechaIngreso;
	}
	public void setEmpleadoFechaIngreso(LocalDate empleadoFechaIngreso) {
		this.empleadoFechaIngreso = empleadoFechaIngreso;
	}
	
	
	

}
