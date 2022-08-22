package pe.confianza.colaboradores.gcontenidos.server.util;

public class ParametrosConstantes {
	
	public class Genericos {
		
		public static final String ANIO_PRESENTE = "PARAMGEN001";
		
	}
	
	public static class Vacaciones {	
		
	}
	
	public static class VacacionesPoliticaRegulatoria {
		public static final String DIAS_MAXIMO_VACACIONES_ADELANTADAS = "PARAMVAC012";
		public static final String MESES_MINIMO_ANTIGUEDAD_VACACIONES_ADELANTADAS = "PARAMVAC013";
		public static final String DIAS_MINIMOS_TRAMOS_PRIMERA_MITAD = "PARAMVAC025";
		public static final String TOTAL_VACACIONES_ANIO = "PARAMVAC026";
		public static final String LIMITE_TIEMPO_INDEMNIZABLE = "PARAMVAC027";
		public static final String SABADOS_MIN_POR_PERIODO = "PARAMVAC028";
		public static final String DOMINGOS_MIN_POR_PERIODO = "PARAMVAC029";
	}
	
	public static class VacacionesProgramacionAnual {
		public static final String FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = "PARAMVAC001";
		public static final String FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = "PARAMVAC002";
		public static final String FECHA_MAXIMA_APROBACION = "PARAMVAC040";
		
		
	}
	
	public static class VacacionesReprogramacion {
		public static final String DIA_INICIO_REPROGRAMACION = "PARAMVAC004";
		public static final String DIA_FIN_REPROGRAMACION = "PARAMVAC005";
		public static final String DIA_LIMITE_APROBACION_REPROGRAMACION = "PARAMVAC024";
		public static final String CANTIDAD_MAX_REPROGRAMACIONES_ANIO = "PARAMVAC030";
		
	}
	
	public static class VacacionesEstado {
		public static final String[] ESTADOS_PROGRAMACION;
		
		static {
			ESTADOS_PROGRAMACION = new String[] {"PARAMVAC006", "PARAMVAC007", "PARAMVAC008", "PARAMVAC009", "PARAMVAC010", "PARAMVAC011" };
		}
		
	}
	
	public static class VacacionesNotificacion {
		public static final String HORA_ENVIO_NOTIFICACIONES_VACACIONES = "PARAMVAC003";
		public static final String DIAS_INTERVALO_NOTIFICACIONES = "PARAMVAC022";
		public static final String DIA_NOTIFICACION_REPROGRAMACION = "PARAMVAC023";
		public static final String MENSAJE_COLABORDOR_INICIO_PROGRAMACION = "PARAMVAC041";
		public static final String MENSAJE_COLABORDOR_META_INCOMPLETA = "PARAMVAC042";
		public static final String MENSAJE_COLABORDOR_SIN_REGISTRO = "PARAMVAC043";
		public static final String MENSAJE_JEFE_SIN_REGISTRO_PROGRAMACIONES = "PARAMVAC044";
		public static final String MENSAJE_JEFE_PENDIENTE_APROBACION = "PARAMVAC045";
		public static final String MENSAJE_COLABORDOR_REPROGRAMACION = "PARAMVAC046";
	}
	
	public static class Publicaciones {
		
		
		
	}
	
	public static class Gastos{
		public static final String GASTO_MENOR_MONTO_MAXIMO = "PARAMVAC047";
	}
	
	/*static {
		Vacaciones.ESTADOS_PROGRAMACION = new String[]{"", ""};
	}*/

}
