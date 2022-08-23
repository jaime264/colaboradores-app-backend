package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

public interface IReporteExcepcion {

	String getCodigo();
	String getNombrecompleto();
	String getCorredor();
	String getAgencia();
	String getPuesto();
	LocalDate getFechainicio();
	LocalDate getFechafin();
	Boolean getAnulacion();
	Boolean getInterrupcion();
	
}
