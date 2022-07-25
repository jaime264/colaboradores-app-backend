package pe.confianza.colaboradores.gcontenidos.server.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.MessageSource;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

public class Utilitario {
	
	/**
	 * Obtiene la deiferencia de dias entre dos fechas
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@Deprecated
	public static int obtenerDiferenciaDias(Date fechaInicio, Date fechaFin) {
		return (int)(TimeUnit.DAYS.convert(fechaFin.getTime() - fechaInicio.getTime(), TimeUnit.MILLISECONDS));
	}
	
	/**
	 * Obtiene la diferencia de dias entre dos fechas
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public static int obtenerDiferenciaDias(LocalDate fechaInicio, LocalDate fechaFin) {
		return (int)(ChronoUnit.DAYS.between(fechaInicio, fechaFin)) + 1;
	}
	
	
	/**
	 *Se obtiene el equivalente en LocalDate de milisegundos
	 * @param milisegundos
	 * @return
	 */
	public static LocalDate obtenerLocalDate(Long milisegundos) {
		if(milisegundos == null)
			return null;
		return Instant.ofEpochMilli(milisegundos).atZone(ZoneId.of(Constantes.TIME_ZONE)).toLocalDate();
	}
	
	/**
	 * Se obtiene el equivalente en Date de milisegundos
	 * @param milisegundos
	 * @return
	 */
	@Deprecated
	public static Date obtenerFecha(long milisegundos) {
		return new Date(milisegundos);
	}
	
	/**
	 * Obtener lista de periodos de vacaciones desde fecha de ingreso
	 * @param fechaIngreso
	 * @return
	 */
	@Deprecated
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
	
	/**
	 * Obtener cantidad de días sabados
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@Deprecated
	public static int obtenerCantidadSabados(Date fechaInicio, Date fechaFin) {
		int numeroSabados = 0;
		Calendar calendarInicio = getCalendarWithoutTime(fechaInicio);
		Calendar calendarFin = getCalendarWithoutTime(fechaFin);
		
		while (calendarInicio.before(calendarFin)) {
			Date dia = calendarInicio.getTime();
			if(dia.getDay() == Calendar.SATURDAY) {
				numeroSabados ++;
			}
		}
		return numeroSabados;
	}
	
	/**
	 * Obtener cantidad de días sabados
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public static int obtenerCantidadSabados(LocalDate fechaInicio, LocalDate fechaFin) {
		Predicate<LocalDate> eSsabado = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY;
		int diasEntre = obtenerDiferenciaDias(fechaInicio, fechaFin);
		
		return Stream.iterate(fechaInicio, date -> date.plusDays(1L)).limit(diasEntre)
			.filter(eSsabado)
			.collect(Collectors.toList()).size();
	}
	
	/**
	 * Obtener cantidad de días domingos
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@Deprecated
	public static int obtenerCantidadDomingos(Date fechaInicio, Date fechaFin) {
		int numeroSabados = 0;
		Calendar calendarInicio = getCalendarWithoutTime(fechaInicio);
		Calendar calendarFin = getCalendarWithoutTime(fechaFin);
		
		while (calendarInicio.before(calendarFin)) {
			Date dia = calendarInicio.getTime();
			if(dia.getDay() == Calendar.SUNDAY) {
				numeroSabados ++;
			}
		}
		return numeroSabados;
	}
	
	/**
	 * Obtener cantidad de días domingos
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public static int obtenerCantidadDomingos(LocalDate fechaInicio, LocalDate fechaFin) {
		Predicate<LocalDate> eSDomingo = date -> date.getDayOfWeek() == DayOfWeek.SUNDAY;
		int diasEntre = obtenerDiferenciaDias(fechaInicio, fechaFin);
		
		return Stream.iterate(fechaInicio, date -> date.plusDays(1L)).limit(diasEntre)
			.filter(eSDomingo)
			.collect(Collectors.toList()).size();
	}
	

	
	/**
	 * Valida si una fecha esta dentro de la fechaInicio y fechaFin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param fecha
	 * @return
	 */
	@Deprecated
	public static boolean fechaEntrePeriodo(Date fechaInicio, Date fechaFin, Date fecha) {
		if(fechaInicio.getTime() >= fecha.getTime() && fechaFin.getTime() <= fecha.getTime()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Valida si una fecha esta dentro de la fechaInicio y fechaFin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param fecha
	 * @return
	 */
	public static boolean fechaEntrePeriodo(LocalDate fechaInicio, LocalDate fechaFin, LocalDate fecha) {
		if(fechaInicio.minusDays(1).isBefore(fecha) && fechaFin.plusDays(1).isAfter(fecha))
			return true;
		return false;
	}
	
	/**
	 * Calcula el derecho de vacaciones hasta la fecha de corte
	 * @param fechaIngreso
	 * @param fechaCorte
	 * @return
	 */
	public static double calcularDerechoVacaciones(LocalDate fechaIngreso, LocalDate fechaCorte) {
		double derecho = 0;
		final double diasPorMes = 2.5;
		LocalDate[] periodoTrunco = rangoPeriodoTrunco(fechaIngreso);
		LocalDate fechaInicioPeriodoMensual = periodoTrunco[0];
		LocalDate fechaFinPeriodoMensual = periodoTrunco[0].plusMonths(1).minusDays(1);
		int diferenciaDias = 0;
		while (fechaFinPeriodoMensual.isBefore(fechaCorte)) {
			fechaFinPeriodoMensual = fechaInicioPeriodoMensual.plusMonths(1).minusDays(1);
			if(fechaFinPeriodoMensual.isBefore(fechaCorte) || fechaFinPeriodoMensual.equals(fechaCorte)) {
				derecho += diasPorMes;
			} else {
				diferenciaDias = obtenerDiferenciaDias(fechaInicioPeriodoMensual, fechaCorte);
				derecho += diferenciaDias  * diasPorMes / 30;
			}
			fechaInicioPeriodoMensual = fechaInicioPeriodoMensual.plusMonths(1);
		}
		return derecho;
	}
	
	public static LocalDate[] rangoPeriodoVencido(LocalDate fechaIngreso) {
		LocalDate fechaActual = LocalDate.now();
		if(fechaIngreso.getYear() < fechaActual.getYear()) {
			LocalDate[] periodoVencido = rangoPeriodoTrunco(fechaIngreso);
			periodoVencido[0] = periodoVencido[0].plusYears(-1);
			periodoVencido[1] = periodoVencido[1].plusYears(-1);
			return periodoVencido;
		}
		return null;
		
	}
	
	public static LocalDate[] rangoPeriodoTrunco(LocalDate fechaIngreso) {
		LocalDate[] periodoTrunco = new LocalDate[2];
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaInicioPeriodoTrunco = fechaIngreso.plusYears(fechaActual.getYear() - fechaIngreso.getYear());
		if(fechaInicioPeriodoTrunco.isAfter(fechaActual))
			fechaInicioPeriodoTrunco = fechaInicioPeriodoTrunco.plusYears(-1);
		LocalDate fechaFinPeriodoTrunco = fechaInicioPeriodoTrunco.plusYears(1).plusDays(-1);
		periodoTrunco[0] = fechaInicioPeriodoTrunco;
		periodoTrunco[1] = fechaFinPeriodoTrunco;
		return periodoTrunco;
	}
	
	/**
	 * Ordena las programacioens de manera ascendente por el campo orden
	 * @param programaciones
	 * @return
	 */
	public static List<VacacionProgramacion> ordenarProgramaciones(List<VacacionProgramacion> programaciones) {
		programaciones.sort(Comparator.comparing(VacacionProgramacion::getOrden));
		return programaciones;
	}
	
	public static LocalDate obtenerFechaLimiteIndemnizablePeriodo(LocalDate fechaIngreso, int anioPeriodo) {
		LocalDate fechaLimiteIdemnizablePeriodo =  fechaIngreso.plusYears(anioPeriodo - fechaIngreso.getYear() + 2);
		return fechaLimiteIdemnizablePeriodo;
	}
	
	public static LocalDate obtenerFechaLimitePeriodo(LocalDate fechaIngreso, int anioPeriodo) {
		LocalDate fechaLimitePeriodo =  fechaIngreso.plusYears(anioPeriodo - fechaIngreso.getYear() + 1);
		return fechaLimitePeriodo;
	}
	
	public static double calcularMetaVacaciones(LocalDate fechaIngreso, double diasVencidos) {
		int mesIngreso = fechaIngreso.getMonthValue();
		return diasVencidos + MetaVacacion.cantidadDias(mesIngreso);
	}
	
	public static double calcularDiasPendientesPorRegistrar(PeriodoVacacion periodo) {
		return periodo.getDerecho() - (
				periodo.getDiasGozados() +
				periodo.getDiasAprobadosGozar() +
				periodo.getDiasRegistradosGozar()
				);
	}
	
	public static double calcularDiasPendientesPorRegistrarEnRegistroProgramacion(int totalDiasVacacionesAnio, PeriodoVacacion periodo) {
		return totalDiasVacacionesAnio - (
				periodo.getDiasGozados() +
				periodo.getDiasAprobadosGozar() +
				periodo.getDiasRegistradosGozar()
				);
	}
	
	public static String obtenerMensaje(MessageSource source, String codigo, Object ...valores) {
		valores = valores == null ? new String[] {} : valores;
		try {
			return source.getMessage(codigo, valores, Constantes.LOCALE_PER);
		} catch (Exception e) {
			return "Mensaje no encontrado";
		}
	}
	
	public static String generarMensajeNotificacion(MessageSource source, String mensaje, Object ...valores) {
		valores = valores == null ? new String[] {} : valores;
		try {
			StringBuilder mensajeSb = new StringBuilder();
			String[] mensajeeArray = mensaje.split(" ");
			int contadorParametro = 0;
			for (String string : mensajeeArray) {
				if(string.equals("XX")) {
					mensajeSb.append("{").append(contadorParametro).append("}")
					.append(" ");
					contadorParametro++;
				} else {
					mensajeSb.append(string).append(" ");
				}
			}
			return source.getMessage(mensajeSb.toString().trim(), valores, Constantes.LOCALE_PER);
		} catch (Exception e) {
			return "Error en generar mensaje";
		}
	}
	
	public static LocalDate agregarDias(LocalDate fecha, int numeroDias) {
		return fecha.plusDays(numeroDias - 1);
	}
	
	public static LocalDate quitarDias(LocalDate fecha, int numeroDias) {
		numeroDias = (numeroDias - 1) * -1;
		return fecha.plusDays(numeroDias);
	}
	
	public static double redondearMeta(double valor) {
		int intPart = (int) valor;
		if(Math.abs(valor - intPart) > 0.5) {
			return intPart + 1;
		}
		return intPart;
	}
	
	private static Calendar getCalendarWithoutTime(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	

}
