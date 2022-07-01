package pe.confianza.colaboradores.gcontenidos.server.util;

public class ParametrosConstantes {
	
	public class Genericos {
		
		public static final String ANIO_PRESENTE = "PARAMGEN001";
		
	}
	
	public static class Vacaciones {
		
		public static final String FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = "PARAMVAC001";
		public static final String FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = "PARAMVAC002";
		public static final String HORA_ENVIO_NOTIFICACIONES_VACACIONES = "PARAMVAC003";
		public static final String DIA_INICIO_REPROGRAMACION = "PARAMVAC004";
		public static final String DIA_FIN_REPROGRAMACION = "PARAMVAC005";
		public static final String[] ESTADOS_PROGRAMACION;
		
		static {
			ESTADOS_PROGRAMACION = new String[] {"PARAMVAC006", "PARAMVAC007", "PARAMVAC008", "PARAMVAC009", "PARAMVAC010", "PARAMVAC011" };
		}
		
	}
	
	public static class Publicaciones {
		
		
		
	}
	
	/*static {
		Vacaciones.ESTADOS_PROGRAMACION = new String[]{"", ""};
	}*/

}
