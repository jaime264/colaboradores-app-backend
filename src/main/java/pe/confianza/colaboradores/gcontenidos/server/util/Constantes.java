package pe.confianza.colaboradores.gcontenidos.server.util;

import org.springframework.beans.factory.annotation.Autowired;

import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;

public class Constantes {
	

	
	public static final String OK = "OK";
	public static final Integer COD_OK = 0;
	public static final Integer COD_EMPTY = 1;
	public static final Integer COD_ERR = 99;
	public static final String DATA_OK = "OK";
	public static final String DATA_EMPTY = "No hay data para esta consulta";
	
	public final static String FORMATO_FECHA = "dd/MM/yyyy";
	public final static String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
	public final static String FORMATO_FECHA_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	public final static String TIME_ZONE = "America/Bogota";

	public class POSICION_EXCEL {
		public static final int POSICION_CABECERA = 0;
		public static final int POSICION_CODIGO_SPRING = 1;
		public static final int POSICION_USUARIO_BT = 2;
		public static final int POSICION_DIAS_VENCIDOS = 13;
		public static final int POSICION_FECHA_VENCIDOS = 14;
		public static final int POSICION_DIAS_TRUNCOS = 15;
		public static final int POSICION_FECHA_TRUNCOS = 16;
	}
	
	
	public class ParametrosCodigos {

		
		public static final String FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = "FECHA_INICIO_REG_PROG_VACACIONES";		
		public static final String FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = "FECHA_FIN_REG_PROG_VACACIONES";
	}
	
	public class EstadoSpring {
		public static final String ACTIVO = "A";
		public static final String INACTIVO = "I";
	}
	
}
