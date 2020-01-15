package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class ConceptoRemu implements Serializable{
	private int id;
	private String descripcion;
	private double monto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5250329981820292310L;

}
