package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

public class ReaccionPost implements Serializable {
	
	private Integer codigo;
	private String nombre;
	private Integer contador;
	private Integer activo;

	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Integer getContador() {
		return contador;
	}


	public void setContador(Integer contador) {
		this.contador = contador;
	}


	public Integer getActivo() {
		return activo;
	}


	public void setActivo(Integer activo) {
		this.activo = activo;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
