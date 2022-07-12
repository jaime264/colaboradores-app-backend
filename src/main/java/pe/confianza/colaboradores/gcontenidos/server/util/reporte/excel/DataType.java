package pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel;

public enum DataType {
	
	INTEGER("Integer"),
	DOUBLE("Double"),
	STRING("String"),
	DATE("Date");
	
	private DataType(String label) {
		this.label = label;
	}

	private final String label;

	public String getLabel() {
		return label;
	}

}
