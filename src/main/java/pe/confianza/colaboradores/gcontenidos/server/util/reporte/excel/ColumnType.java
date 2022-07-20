package pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel;

/**
 * 
 * @author kenyo.pecho
 *
 */
public enum ColumnType {
	
	INTEGER("Integer"),
	DOUBLE("Double"),
	STRING("String"),
	DATE("Date");
	
	private ColumnType(String label) {
		this.label = label;
	}

	private final String label;

	public String getLabel() {
		return label;
	}

}
