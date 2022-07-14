package pe.confianza.colaboradores.gcontenidos.server.util;

public enum ParametroUnidad {
	
	MESES("MESES", "Meses", Integer.class),
	ANIO("ANIO", "Años", Integer.class),
	DIA_MES_FECHA("DIA_MES_FECHA","Dia/Mes", String.class),
	INTERVALO_DIAS("INTERVALO_DIAS", "Días", Integer.class),
	HORA("HORA", "Hora", Integer.class),
	DIA_MES("DIA_MES", "de cada mes", String.class),
	TEXTO_GENERAL("TEXTO_GENERAL", "", String.class ),
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
