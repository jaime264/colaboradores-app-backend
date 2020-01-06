package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class ReaccionPost implements Serializable {
	
	private Integer id;
	private String nombre;
	private Integer contador;
	private Integer activo;

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


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
