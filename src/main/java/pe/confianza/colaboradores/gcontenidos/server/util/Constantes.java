package pe.confianza.colaboradores.gcontenidos.server.util;


import java.util.Locale;


public class Constantes {
	

	
	public static final String OK = "OK";
	public static final Integer COD_OK = 0;
	public static final Integer COD_EMPTY = 1;
	public static final Integer COD_NO_AUTORIZADO = 2;
	public static final Integer COD_ERR = 99;
	public static final String DATA_OK = "OK";
	public static final String DATA_EMPTY = "No hay data para esta consulta";
	public static final String DATA_NO_AUTORIZADO= "Usuario de sesión no concuerda con el de consulta";
	
	public final static String FORMATO_FECHA = "dd/MM/yyyy";
	public final static String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
	public final static String FORMATO_FECHA_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final Locale LOCALE_PER = new Locale("es", "PE");
	public final static String FORMATO_DIA_MES_LITERAL = "dd 'de' MMMM";
	
	public final static String TIME_ZONE = "America/Bogota";
	
	public final static String VACACIONES_PROCESO = "020";
	public final static String VACACIONES_APROBACIONES_PROCESO = "021";
	public final static String VACACIONES_REPROGRAMACIONES_PROCESO = "022";
	public final static String VACACIONES_EXCEPCIONES_PROCESO = "023";
	public final static String VACACIONES_PARAMETRIA_PROCESO = "024";
	public final static String GASTOS_GESTIONAR_PRESUPUESTO = "025";
	
	//RED COMERCIAL
	public final static String ASESOR_NEGOCIO = "ASESOR DE NEGOCIO";
	public final static String ASESOR_NEGOCIO_INDIVIDUAL = "ASESOR DE NEGOCIO INCLUSIVO INDIVIDUAL";
	public final static String ASESOR_NEGOCIO_GRUPAL = "ASESOR DE NEGOCIO INCLUSIVO GRUPAL";
	public final static String GERENTE_CORREDOR = "GERENTE DE CORREDOR";
	public final static String ADMINISTRADOR_NEGOCIO = "ADMINISTRADOR DE NEGOCIO";
	
	//OPERACIONES
	public final static String ASESOR_SERVICIO = "ASESOR DE SERVICIO";
	public final static String SUPERVISOR_OFICINA = "SUPERVISOR DE OFICINA";
	public final static String ASESOR_PLATAFORMA = "ASESOR DE PLATAFORMA";
	
	
	//RECUPERACIONES
	public final static String ANALISTA_COBRANZA = "ANALISTA DE COBRANZA";
	public final static String ANALISTA_RECUPERACIONES = "ANALISTA DE RECUPERACIONES";
	public final static String RESPONSABLE_DEPARTAMENTO_COBRANZA = "RESPONSABLE DE DEPARTAMENTO DE COBRANZA";

	//STAF LOS QUE SOBRAN
	
	//TERRITORIOS
	public final static String CENTRO_SUR = "CENTRO SUR";
	public final static String LIMA_ORIENTE = "LIMA ORIENTE";
	public final static String NOR_ANDINO = "NOR ANDINO";
	public final static String CORPORATIVO = "CORPORATIVO";
	
	//GASTOS
	public final static String GASTOS_MENORES = "GAT001";
	
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
	
	public class EstadoPublicacion{		
		public static final int RECHAZADO = 0;
		public static final int ACEPTADO = 1;
		public static final int OBSERVADO = 2;
	}
	
}
