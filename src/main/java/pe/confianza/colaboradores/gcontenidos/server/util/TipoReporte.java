package pe.confianza.colaboradores.gcontenidos.server.util;

public enum TipoReporte {
	
	TOTAL_DIAS_META("RE001"),
	RED_COMERCIAL("RE002");
	
	public String codigo;
	
	private TipoReporte(String codigo) {
		this.codigo = codigo;
	}
	
	

}
