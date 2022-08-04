package pe.confianza.colaboradores.gcontenidos.server.util;

public enum TipoReporte {
	
	TOTAL_DIAS_META("RE001"),
	RED_COMERCIAL("RE002"),
	RED_PRODUCCION_TECNOLOGIA("RE003"),
	STAFF("RE004"),
	TOTAL_COLECTIVOS("RE005"),
	TOTAL_TERRITORIOS("RE006"),
	TOTAL_POR_TERRITORIO("RE007");
	
	public String codigo;
	
	private TipoReporte(String codigo) {
		this.codigo = codigo;
	}
	
	

}
