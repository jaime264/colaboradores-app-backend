package pe.confianza.colaboradores.gcontenidos.server.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseReporteMeta {
	
	private String categoria;
	private int meta;
	private int diasGozados;
	private int diasPendientes;
	private double porcentajeAvance;

}
