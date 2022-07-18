package pe.confianza.colaboradores.gcontenidos.server.util;

import java.text.SimpleDateFormat;

public enum ParametroUnidad {
	
	DIAS("DIAS", "días", Integer.class),
	MESES("MESES", "meses", Integer.class),
	ANIO("ANIO", "años", Integer.class),
	DIA_MES_FECHA("DIA_MES_FECHA","Dia/Mes", String.class),
	INTERVALO_DIAS("INTERVALO_DIAS", "Días", Integer.class),
	HORA("HORA", "hora", Integer.class),
	DIA_MES("DIA_MES", "de cada mes", Integer.class),
	ESTADO_VACACION("ESTADO_VACACION", "", String.class),
	TEXTO_GENERAL("TEXTO_GENERAL", "", String.class ),
	NUMERO_GENERAL("NUMERO_GENERAL", "", String.class ),
	PORCENTAJE("PORCENTAJE", "%", Double.class);
	
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
					int mes = Integer.parseInt(fecha[1]);
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
		}
		return true;
	}

}
