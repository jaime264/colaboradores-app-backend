package pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel.xlsx;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel.ColumnType;
import pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel.FeatureCollectionCreator;
import pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel.collection.Collection;

/**
 * 
 * @author kenyo.pecho
 *
 */
public class XLSXFeatureCollection extends FeatureCollectionCreator {

public static final String XLS_HEADER_LINE = "XLS_HEADER_LINE";
	
	public XLSXFeatureCollection(Path filePath) throws FileNotFoundException {
		super(filePath);
	}

	public XLSXFeatureCollection(InputStream inputStream) throws FileNotFoundException {
		super(inputStream);
	}
	
	protected void read(Collection collection) {
		XSSFWorkbook workBook;
		try {
			workBook = new XSSFWorkbook(this.inputStream);
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				XSSFSheet sheet = workBook.getSheetAt(i);
				Map<Integer, String> mapIdHeader = new LinkedHashMap<>();
				boolean isPrimeraColumna = true;
				int numeroColumnas = 0;
				int startLine = Integer.parseInt(getProperties().getProperty(XLS_HEADER_LINE, String.valueOf(0)));
				for (int k = startLine; k < sheet.getLastRowNum() + 1; k++) {
					XSSFRow row = sheet.getRow(k);
					if (isPrimeraColumna) {
						numeroColumnas = row.getLastCellNum();
					} else {
						if (sheet.getRow(k) == null) { 
							continue;
						}else{
							collection.newRow();
						}				
					}
					
					for (int j = 0; j < numeroColumnas; j++) {
						Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
						
						if (isPrimeraColumna) {			
							String header_clean = cell.toString().toLowerCase().replaceAll("[^a-zA-Z0-9]+", "");
							final String header = customizeHeader(header_clean, 0);
							XSSFRow _row = sheet.getRow(k+1);
							Cell _cell = _row.getCell(j, Row.CREATE_NULL_AS_BLANK);
							switch (_cell.getCellType()) {
								case Cell.CELL_TYPE_NUMERIC:
									collection.addHeader(header, ColumnType.DOUBLE);
									break;
								case Cell.CELL_TYPE_STRING:
									collection.addHeader(header, ColumnType.STRING);
									break;
								default:
									collection.addHeader(header, ColumnType.STRING);
									break;
							}
							mapIdHeader.put(j, header);
							continue;
						}
						// 4. Si esta en blanco crear celda null y asignarle
						// valor en blanco
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							switch (collection.getHeaders().get(mapIdHeader.get(j)).name()) {
								case "STRING":
									cell.setCellType(Cell.CELL_TYPE_STRING);
									collection.getCurrentRow().addCell(mapIdHeader.get(j), "");
									break;
								case "DOUBLE":
									collection.getCurrentRow().addCell(mapIdHeader.get(j), 0);
									break;
								default:
									collection.getCurrentRow().addCell(mapIdHeader.get(j), "");
									break;
							}
						} else {
							switch (collection.getHeaders().get(mapIdHeader.get(j)).name()) {
								case "STRING":
									cell.setCellType(Cell.CELL_TYPE_STRING);
									collection.getCurrentRow().addCell(mapIdHeader.get(j), cell.toString());
									break;
								case "DOUBLE":
									collection.getCurrentRow().addCell(mapIdHeader.get(j), Double.parseDouble(cell.toString()));
									break;
								default:
									collection.getCurrentRow().addCell(mapIdHeader.get(j), "");
									break;
							}
						}
					}
					if (!isPrimeraColumna)
						collection.addRow();
					
					isPrimeraColumna = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private String customizeHeader(String header, int copy) {
		String header_ = header;
		if (header.trim().isEmpty())
			header = "default";

		int copyNumber = validateHeader(header, copy);
		if (copyNumber != 0)
			header_ = header + "_" + copyNumber;
		else
			header_ = header;
		return header_;
	}

	private int validateHeader(String header, int copy) {
		String header_ = header;
		if (copy != 0)
			header_ = header + "_" + copy;
		else
			header_ = header;

		if (getCollection().getHeaders().containsKey(header_)) {
			return validateHeader(header, copy + 1);
		} else {
			return copy;
		}

	}
}
