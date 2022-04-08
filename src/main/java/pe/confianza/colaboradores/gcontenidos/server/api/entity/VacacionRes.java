package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class VacacionRes {

	private String fechaIngreso;

	private String fechaFinContrato;

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFincontrato(String fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}

}
