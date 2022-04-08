package pe.confianza.colaboradores.gcontenidos.server.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utilitario {
	
	/**
	 * Obtiene la diferencai de dias entre dos fechas
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public static int obtenerDieferenciaDias(Date fechaInicio, Date fechaFin) {
		return (int)(TimeUnit.DAYS.convert(fechaFin.getTime() - fechaInicio.getTime(), TimeUnit.MILLISECONDS));
	}
	
	/**
	 * Se obtiene el equivalente en Date de milisegundos
	 * @param milisegundos
	 * @return
	 */
	public static Date obtenerFecha(long milisegundos) {
		return new Date(milisegundos);
	}
	
	/**
	 * Obtener lista de periodos de vacaciones desde fecha de ingreso
	 * @param fechaIngreso
	 * @return
	 */
	public static List<String> obtenerPeriodosVacaciones(Date fechaIngreso) {
		List<String> periodos = new ArrayList<>();
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		int anioIngreso = fechaIngreso.getYear() + 1900;
		if(anioIngreso < anioActual) {
			for (int i = anioIngreso; i <= anioActual; i++) {
				periodos.add(i + " - " + (i + 1));
			}
		} else {
			periodos.add(anioActual + " - " + (anioActual + 1));
		}
		return periodos;
	}
	
	

}
