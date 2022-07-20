package pe.confianza.colaboradores.gcontenidos.server.util.file.collection;

import java.util.LinkedHashMap;
import java.util.Map;

public class Row {
	
	private Map<String, Object> row;
	
	public Row() {
		this.row = new LinkedHashMap<>();
	}
	
	public void addCell(String column, Object value) {
		this.row.put(column, value);
	}
	
	public void editCell(String column, Object value) {
		this.row.put(column, value);
	}
	
	public Object getCellValue(String column) {
		return this.row.get(column);
	}
	
	public Map<String, Object> getRow() {
		return row;
	}

}
