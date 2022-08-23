package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseReporteExcepciones {

	String codigo;
	String nombrecompleto;
	String area;
	String agencia;
	String puesto;
	LocalDate fechainicio;
	LocalDate fechafin;
	long numeroDiasGozar;
	String tipoExcepcion;
	int excepcion;
}
