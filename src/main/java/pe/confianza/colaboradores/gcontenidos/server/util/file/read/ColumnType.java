package pe.confianza.colaboradores.gcontenidos.server.util.file.read;

/**
 * 
 * @author kenyo.pecho
 *
 */
public enum ColumnType {
	
	INTEGER("Integer"),
	DOUBLE("Double"),
	STRING("String"),
	LOCALDATE("LocalDate"),
	LOCALDATETIME("LocalDateTime");
	
	private ColumnType(String label) {
		this.label = label;
	}

	private final String label;

	public String getLabel() {
		return label;
	}

}
