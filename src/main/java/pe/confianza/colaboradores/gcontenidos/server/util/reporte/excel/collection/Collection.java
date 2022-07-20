package pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel.ColumnType;

public class Collection {

	private Map<String, ColumnType> headers;
	private List<Row> rows;
	private Row currentRow;
	
	public Collection(Map<String, ColumnType> headers, List<Row> rows, Row currentRow) {
		super();
		this.headers = headers;
		this.rows = rows;
		this.currentRow = currentRow;
	}
	
	public Collection() {
		this.headers = new LinkedHashMap<String, ColumnType>();
		this.rows = new ArrayList<Row>();
	}

	public void newRow() {
		this.currentRow = new Row();		
		Iterator<String> it = this.headers.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			this.currentRow.addCell(key, null);
		}
	}

	public void addHeader(String column, ColumnType columnType) {
		this.headers.put(column, columnType);
	}

	public void editHeaderType(String column, ColumnType columnType) {
		addHeader(column, columnType);
	}

	public void addRow() {
		this.rows.add(currentRow);
	}

	public Map<String, ColumnType> getHeaders() {
		return headers;
	}

	public List<Row> getRows() {
		return rows;
	}

	public Row getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(Row currentRow) {
		this.currentRow = currentRow;
	}
}
