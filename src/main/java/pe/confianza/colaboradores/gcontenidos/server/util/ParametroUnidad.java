package pe.confianza.colaboradores.gcontenidos.server.util;

public enum ParametroUnidad {
	
	DIAS("DIAS", "días", Integer.class),
	MESES("MESES", "meses", Integer.class),
	ANIO("ANIO", "años", Integer.class),
	DIA_MES_FECHA("DIA_MES_FECHA","Dia/Mes", String.class),
	INTERVALO_DIAS("INTERVALO_DIAS", "Días", Integer.class),
	HORA("HORA", "hora", Integer.class),
	DIA_MES("DIA_MES", "de cada mes", Integer.class),
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
	
	public static String getDescripcionPorCodigo(String codigo) {
		for (ParametroUnidad unidad : ParametroUnidad.values()) {
			if(unidad.codigo.equals(codigo))
				return unidad.descripcion;
		}
		return null;
	}

}
