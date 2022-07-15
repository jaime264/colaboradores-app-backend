package pe.confianza.colaboradores.gcontenidos.server.controller;

import pe.confianza.colaboradores.gcontenidos.server.RequestPaginacion;

public class RequestFiltroEmpleadoMeta extends RequestPaginacion {
	
	private Long idPuesto;
	private String nombre;
	
	public long getIdPuesto() {
		return idPuesto;
	}
	public void setIdPuesto(long idPuesto) {
		this.idPuesto = idPuesto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
