package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT "
		+ "empleado_acceso_id, empleado_id, empleado_usuariobt, "
		+ "perfil_spring_id, perfil_spring_descripcion, perfil_spring_codigo, "
		+ "perfil_app_id, perfil_app_descripcion, perfil_app_codigo, "
		+ "funcionalidad_id, funcionalidad_descripcion, funcionalidad_codigo, "
		+ "funcionalidad_acceso_id, funcionalidad_acceso_consultar, funcionalidad_acceso_registrar, "
		+ "funcionalidad_acceso_eliminar, funcionalidad_acceso_modificar,"
		+ " funcionalidad_acceso_aprobar FROM empleado_acceso")
public class EmpleadoAcceso {
	
	@Id
	private String empleadoAccesoId;
	private long empleadoId;
	private String empleadoUsuariobt;
	
	private long perfilSpringId;
	private String perfilSpringDescripcion;
	private String perfilSpringCodigo;
	
	private long perfilAppId;
	private String perfilAppDescripcion;
	private String perfilAppCodigo;
	
	private long funcionalidadId;
	private String funcionalidadDescripcion;
	private String funcionalidadCodigo;
	
	private long funcionalidadAccesoId;
	private boolean funcionalidadAccesoConsultar;
	private boolean funcionalidadAccesoRegistrar;
	private boolean funcionalidadAccesoEliminar;
	private boolean funcionalidadAccesoModificar;
	private boolean funcionalidadAccesoAprobar;
	public String getEmpleadoAccesoId() {
		return empleadoAccesoId;
	}
	public void setEmpleadoAccesoId(String empleadoAccesoId) {
		this.empleadoAccesoId = empleadoAccesoId;
	}
	public long getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(long empleadoId) {
		this.empleadoId = empleadoId;
	}
	public String getEmpleadoUsuariobt() {
		return empleadoUsuariobt;
	}
	public void setEmpleadoUsuariobt(String empleadoUsuariobt) {
		this.empleadoUsuariobt = empleadoUsuariobt;
	}
	public long getPerfilSpringId() {
		return perfilSpringId;
	}
	public void setPerfilSpringId(long perfilSpringId) {
		this.perfilSpringId = perfilSpringId;
	}
	public String getPerfilSpringDescripcion() {
		return perfilSpringDescripcion;
	}
	public void setPerfilSpringDescripcion(String perfilSpringDescripcion) {
		this.perfilSpringDescripcion = perfilSpringDescripcion;
	}
	public String getPerfilSpringCodigo() {
		return perfilSpringCodigo;
	}
	public void setPerfilSpringCodigo(String perfilSpringCodigo) {
		this.perfilSpringCodigo = perfilSpringCodigo;
	}
	public long getPerfilAppId() {
		return perfilAppId;
	}
	public void setPerfilAppId(long perfilAppId) {
		this.perfilAppId = perfilAppId;
	}
	public String getPerfilAppDescripcion() {
		return perfilAppDescripcion;
	}
	public void setPerfilAppDescripcion(String perfilAppDescripcion) {
		this.perfilAppDescripcion = perfilAppDescripcion;
	}
	public String getPerfilAppCodigo() {
		return perfilAppCodigo;
	}
	public void setPerfilAppCodigo(String perfilAppCodigo) {
		this.perfilAppCodigo = perfilAppCodigo;
	}
	public long getFuncionalidadId() {
		return funcionalidadId;
	}
	public void setFuncionalidadId(long funcionalidadId) {
		this.funcionalidadId = funcionalidadId;
	}
	public String getFuncionalidadDescripcion() {
		return funcionalidadDescripcion;
	}
	public void setFuncionalidadDescripcion(String funcionalidadDescripcion) {
		this.funcionalidadDescripcion = funcionalidadDescripcion;
	}
	public String getFuncionalidadCodigo() {
		return funcionalidadCodigo;
	}
	public void setFuncionalidadCodigo(String funcionalidadCodigo) {
		this.funcionalidadCodigo = funcionalidadCodigo;
	}
	public long getFuncionalidadAccesoId() {
		return funcionalidadAccesoId;
	}
	public void setFuncionalidadAccesoId(long funcionalidadAccesoId) {
		this.funcionalidadAccesoId = funcionalidadAccesoId;
	}
	public boolean isFuncionalidadAccesoConsultar() {
		return funcionalidadAccesoConsultar;
	}
	public void setFuncionalidadAccesoConsultar(boolean funcionalidadAccesoConsultar) {
		this.funcionalidadAccesoConsultar = funcionalidadAccesoConsultar;
	}
	public boolean isFuncionalidadAccesoRegistrar() {
		return funcionalidadAccesoRegistrar;
	}
	public void setFuncionalidadAccesoRegistrar(boolean funcionalidadAccesoRegistrar) {
		this.funcionalidadAccesoRegistrar = funcionalidadAccesoRegistrar;
	}
	public boolean isFuncionalidadAccesoEliminar() {
		return funcionalidadAccesoEliminar;
	}
	public void setFuncionalidadAccesoEliminar(boolean funcionalidadAccesoEliminar) {
		this.funcionalidadAccesoEliminar = funcionalidadAccesoEliminar;
	}
	public boolean isFuncionalidadAccesoModificar() {
		return funcionalidadAccesoModificar;
	}
	public void setFuncionalidadAccesoModificar(boolean funcionalidadAccesoModificar) {
		this.funcionalidadAccesoModificar = funcionalidadAccesoModificar;
	}
	public boolean isFuncionalidadAccesoAprobar() {
		return funcionalidadAccesoAprobar;
	}
	public void setFuncionalidadAccesoAprobar(boolean funcionalidadAccesoAprobar) {
		this.funcionalidadAccesoAprobar = funcionalidadAccesoAprobar;
	}
	
	
	

}
