package pe.confianza.colaboradores.gcontenidos.server.util;

import java.text.SimpleDateFormat;

public enum ParametroUnidad {
	
	DIAS("DIAS", "días", Integer.class),
	MESES("MESES", "meses", Integer.class),
	ANIO("ANIO", "años", Integer.class),
	DIA_MES_FECHA("DIA_MES_FECHA","Dia/Mes", String.class),
	INTERVALO_DIAS("INTERVALO_DIAS", "Días", Integer.class),
	HORA("HORA", "hora(s)", Integer.class),
	DIA_MES("DIA_MES", "de cada mes", Integer.class),
	ESTADO_VACACION("ESTADO_VACACION", "", String.class),
	TEXTO_GENERAL("TEXTO_GENERAL", "", String.class ),
	NUMERO_MES("NUMERO_MES", "", Integer.class ),
	NUMERO_GENERAL("NUMERO_GENERAL", "", Integer.class ),
	PORCENTAJE("PORCENTAJE", "%", Double.class),
	MENSAJE_UN_PARAMETRO("MENSAJE_UN_PARAMETRO", "TEXT", String.class),
	MENSAJE_DOS_PARAMETROS("MENSAJE_DOS_PARAMETROS", "TEXT", String.class),
	MENSAJE_TRES_PARAMETROS("MENSAJE_TRES_PARAMETROS", "TEXT", String.class),
	MENSAJE_CUATRO_PARAMETROS("MENSAJE_CUATRO_PARAMETROS", "TEXT", String.class);
	
	public String codigo;
	public String descripcion;
	private Class clazz;
	
	private ParametroUnidad(String codigo,String descripcion, Class<?> clazz) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.clazz = clazz;
	}
	
	public static String getDescripcionPorCodigo(String codigoBuscar) {
		for (ParametroUnidad unidad : ParametroUnidad.values()) {
			if(unidad.codigo.equals(codigoBuscar))
				return unidad.descripcion;
		}
		return null;
	}
	
	public static ParametroUnidad buscar(String codigoBuscar) {
		for (ParametroUnidad unidad : ParametroUnidad.values()) {
			if(unidad.codigo.equals(codigoBuscar))
				return unidad;
		}
		return null;
	}
	
	public static boolean esValidoValor(ParametroUnidad unidad, String valor) {
		if(Integer.class == unidad.clazz) {
			try {
				int intValor = Integer.parseInt(valor);
				if(ParametroUnidad.NUMERO_GENERAL.codigo.equals(valor)) {
					if(intValor < 1)
						return false;
				}
				if(ParametroUnidad.HORA.codigo.equals(valor)) {
					if(intValor < 0 || intValor > 23)
						return false;
				}
				if(ParametroUnidad.DIA_MES.codigo.equals(valor)) {
					if(intValor < 1 || intValor > 30)
						return false;
				}
				if(ParametroUnidad.INTERVALO_DIAS.codigo.equals(valor)) {
					if(intValor < 0)
						return false;
				}
				if(ParametroUnidad.DIAS.codigo.equals(valor)) {
					if(intValor < 0)
						return false;
				}
				if(ParametroUnidad.MESES.codigo.equals(valor)) {
					if(intValor < 0)
						return false;
				}
				if(ParametroUnidad.NUMERO_MES.codigo.equals(valor)) {
					if(intValor < 1 || intValor > 12)
						return false;
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		if(Double.class == unidad.clazz) {
			try {
				double dValor = Double.parseDouble(valor);
				if(ParametroUnidad.PORCENTAJE.codigo.equals(valor)) {
					if(dValor < 0 || dValor > 100)
						return false;
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		if(String.class == unidad.clazz) {
			if(valor == null)
				return false;
			if(valor.length() == 0)
				return false;
			if(ParametroUnidad.DIA_MES_FECHA.codigo.equals(unidad.codigo)) {
				if(!valor.contains("/"))
					return false;
				String[] fecha = valor.split("/");
				if(fecha.length != 2)
					return false;
				try {
					int dia = Integer.parseInt(fecha[0]);
					if(dia < 1 || dia > 31)
						return false;
					int mes = Integer.parseInt(fecha[1]);
					if(mes < 1 || mes > 12)
						return false;
					String diaMes = (dia < 10 ? "0"+dia : dia) + "/" + ( mes < 10 ? "0"+mes : mes) + "/2020";
					new SimpleDateFormat("dd/MM/yyyy").parse(diaMes);
					return true;
				} catch (Exception e) {
					return false;
				}
			}
			if(ParametroUnidad.ESTADO_VACACION.codigo.equals(unidad.codigo)) {
				if(!valor.contains("-"))
					return false;
			}
			if(ParametroUnidad.MENSAJE_UN_PARAMETRO.codigo.equals(unidad.codigo)) {
				String[] mensajeArray = valor.split(" ");
				int nroParametros = 0;
				for (String string : mensajeArray) {
					if(string.contains("XX"))
						nroParametros ++;
				}
				if(nroParametros == 1) {
					return true;
				}
				return false;
			}
			if(ParametroUnidad.MENSAJE_DOS_PARAMETROS.codigo.equals(unidad.codigo)) {
				String[] mensajeArray = valor.split(" ");
				int nroParametros = 0;
				for (String string : mensajeArray) {
					if(string.contains("XX"))
						nroParametros ++;
				}
				if(nroParametros == 2) {
					return true;
				}
				return false;
			}
			if(ParametroUnidad.MENSAJE_TRES_PARAMETROS.codigo.equals(unidad.codigo)) {
				String[] mensajeArray = valor.split(" ");
				int nroParametros = 0;
				for (String string : mensajeArray) {
					if(string.contains("XX"))
						nroParametros ++;
				}
				if(nroParametros == 3) {
					return true;
				}
				return false;
			}
			if(ParametroUnidad.MENSAJE_CUATRO_PARAMETROS.codigo.equals(unidad.codigo)) {
				String[] mensajeArray = valor.split(" ");
				int nroParametros = 0;
				for (String string : mensajeArray) {
					if(string.contains("XX"))
						nroParametros ++;
				}
				if(nroParametros == 4) {
					return true;
				}
				return false;
			}
		}
		return true;
	}
	
	public static String procesarNuevoValor(ParametroUnidad unidad, String valor) {
		if(Integer.class == unidad.clazz) {
			try {
				Integer intValor = Integer.parseInt(valor.trim());
				return intValor.toString();
			} catch (Exception e) {
				return "0";
			}
		}
		if(Double.class == unidad.clazz) {
			try {
				Double dValor = Double.parseDouble(valor.trim());
				return dValor.toString();
			} catch (Exception e) {
				return "0";
			}
		}
		if(String.class == unidad.clazz) {
			if(valor == null)
				return "";
			if(valor.length() == 0)
				return "";
			return valor.trim();
		}
		return valor.trim();
	}

}
