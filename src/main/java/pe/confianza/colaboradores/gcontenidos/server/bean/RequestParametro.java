package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestParametro {
	
	@NotNull
	@Size(min = 1, message = "Debe ingresar un código")
	private String codigo;
	
	@NotNull
	@Size(min = 1, message = "Debe ingresar un valor")
	private String valor;
	
	@NotNull
	@Size(min = 1, message = "Debe ingresar una descripción")
	private String descripcion;
	
	private LogAuditoria logAuditoria;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}
	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}

}
