package pe.confianza.colaboradores.gcontenidos.server.util.file.write.xlsx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.confianza.colaboradores.gcontenidos.server.util.file.read.ColumnType;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.IReport;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.Report;

/**
 * 
 * @author kenyo.pecho
 *
 */
public class XlsxReport implements IReport<ByteArrayInputStream> {
	
	private static Logger logger = LoggerFactory.getLogger(XlsxReport.class);
	
	private Report report;
	
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private final String company = "FINANCIERA CONFIANZA";
	
	private boolean hasLogo;
	private boolean hasTitle;
	private boolean hasSubTitle;
	
	private ByteArrayInputStream excelData;
	
	
	public XlsxReport(Report report) {
		this.report = report;
		this.hasLogo = this.report.getLogo() == null ? false : true;
		this.hasTitle = this.report.getTitle() == null ? false : true;
		this.hasSubTitle = this.report.getSubTitle() == null ? false : true;
	}

	@Override
	public void build() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Workbook  book = new XSSFWorkbook();
		try {
			byte[] logoBytes = null;
			int pictureureIdx = 0;
			if(this.hasLogo) {
				pictureureIdx  = book.addPicture(logoBytes, Workbook.PICTURE_TYPE_PNG);
				this.report.getLogo().close();
			}
			
			CreationHelper createHelper = book.getCreationHelper();
			Sheet sheet = book.createSheet("financiera_confianza");
			
			/** BEGIN - INSERT LOGO **/
			if(this.hasLogo) {
				Drawing drawing = sheet.createDrawingPatriarch();
				ClientAnchor anchor = createHelper.createClientAnchor();
				anchor.setCol1(1);
				anchor.setRow1(0);
				
				Picture picture = drawing.createPicture(anchor, pictureureIdx);
				picture.resize(1, 3);
			}
			/** END - INSERT LOGO **/
			
			/** BEGIN - FONT && STYLES **/
			Font titleFont = book.createFont();
			titleFont.setBold(true);
			titleFont.setFontHeightInPoints((short) 14);
			titleFont.setColor(IndexedColors.BLACK.getIndex());
	        
			Font labelFont = book.createFont();
			labelFont.setBold(true);
			labelFont.setFontHeightInPoints((short) 12);
			labelFont.setColor(IndexedColors.BLACK.getIndex());
			
			Font valueFont = book.createFont();
			valueFont.setBold(false);
			valueFont.setFontHeightInPoints((short) 10);
			valueFont.setColor(IndexedColors.BLACK.getIndex());
			
			CellStyle titleStyle = book.createCellStyle();
			titleStyle.setFont(titleFont);
			titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle labelStyle = book.createCellStyle();
			labelStyle.setFont(labelFont);
			labelStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			
			CellStyle simpleStyle = book.createCellStyle();
			simpleStyle.setFont(valueFont);
			simpleStyle.setAlignment(CellStyle.ALIGN_LEFT);
			
			CellStyle headerTableStyle = book.createCellStyle();
			headerTableStyle.setFont(labelFont);
			headerTableStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyle.setBorderBottom(CellStyle.BORDER_THIN);
			headerTableStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyle.setBorderTop(CellStyle.BORDER_THIN);
			headerTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyle.setBorderLeft(CellStyle.BORDER_THIN);
			headerTableStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyle.setBorderRight(CellStyle.BORDER_THIN);
			headerTableStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			headerTableStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			CellStyle headerTableStyleNumber = book.createCellStyle();
			headerTableStyleNumber.setFont(valueFont);
			headerTableStyleNumber.setAlignment(CellStyle.ALIGN_RIGHT);
			headerTableStyleNumber.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyleNumber.setBorderBottom(CellStyle.BORDER_THIN);
			headerTableStyleNumber.setTopBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyleNumber.setBorderTop(CellStyle.BORDER_THIN);
			headerTableStyleNumber.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyleNumber.setBorderLeft(CellStyle.BORDER_THIN);
			headerTableStyleNumber.setRightBorderColor(IndexedColors.BLACK.getIndex());
			headerTableStyleNumber.setBorderRight(CellStyle.BORDER_THIN);
			
			CellStyle valueStringTableStyle = book.createCellStyle();
			valueStringTableStyle.setFont(valueFont);
			valueStringTableStyle.setAlignment(CellStyle.ALIGN_LEFT);
			valueStringTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			valueStringTableStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			valueStringTableStyle.setBorderBottom(CellStyle.BORDER_THIN);
			valueStringTableStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			valueStringTableStyle.setBorderTop(CellStyle.BORDER_THIN);
			valueStringTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			valueStringTableStyle.setBorderLeft(CellStyle.BORDER_THIN);
			valueStringTableStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			valueStringTableStyle.setBorderRight(CellStyle.BORDER_THIN);
			
			CellStyle valueNumberTableStyle = book.createCellStyle();
			valueNumberTableStyle.setFont(valueFont);
			valueNumberTableStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			valueNumberTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			valueNumberTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			valueNumberTableStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			valueNumberTableStyle.setBorderBottom(CellStyle.BORDER_THIN);
			valueNumberTableStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			valueNumberTableStyle.setBorderTop(CellStyle.BORDER_THIN);
			valueNumberTableStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			valueNumberTableStyle.setBorderLeft(CellStyle.BORDER_THIN);
			valueNumberTableStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			valueNumberTableStyle.setBorderRight(CellStyle.BORDER_THIN);
			/** END - FONT && STYLES **/
			
			int rowNum = 1;
			Row row = sheet.createRow(rowNum);
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, this.report.getCollection().getHeaders().size() + 3));
			Cell ctitle = row.createCell(0);
			ctitle.setCellValue(company);
			ctitle.setCellStyle(titleStyle);
			
			
			if(this.hasTitle) {
				rowNum ++;
				row = sheet.createRow(rowNum);
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, this.report.getCollection().getHeaders().size() + 3));
				Cell subtitle = row.createCell(0);
				subtitle.setCellValue(this.report.getTitle());
				subtitle.setCellStyle(titleStyle);
			}
			
			if(this.hasSubTitle) {
				rowNum ++;
				rowNum ++;
				row = sheet.createRow(rowNum);
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, this.report.getCollection().getHeaders().size() + 3));
				Cell subtitle = row.createCell(0);
				subtitle.setCellValue(this.report.getSubTitle());
				subtitle.setCellStyle(titleStyle);
			}
			
			
			rowNum ++;
			rowNum ++;
			row = sheet.createRow(rowNum);
			Iterator<String> itHeaders = this.report.getCollection().getHeaders().keySet().iterator();
			int numCol = 1;
			while(itHeaders.hasNext()) {
				String key = itHeaders.next();	
				Cell cell = row.createCell(numCol);
				cell.setCellValue(key);
				cell.setCellStyle(headerTableStyle);
				numCol++;
			}
			rowNum ++;
			numCol = 1;
			for (pe.confianza.colaboradores.gcontenidos.server.util.file.collection.Row rowData : this.report.getCollection().getRows()) {
				row = sheet.createRow(rowNum);
				itHeaders = this.report.getCollection().getHeaders().keySet().iterator();
				while(itHeaders.hasNext()) {
					String key = itHeaders.next();
					Cell cell = row.createCell(numCol);
					if(this.report.getCollection().getHeaders().get(key) == ColumnType.INTEGER) {
						cell.setCellValue((Integer)rowData.getCellValue(key));
						cell.setCellStyle(valueNumberTableStyle);
					}
					if(this.report.getCollection().getHeaders().get(key) == ColumnType.DOUBLE) {
						cell.setCellValue((Double)rowData.getCellValue(key));
						cell.setCellStyle(valueNumberTableStyle);
					}
					if(this.report.getCollection().getHeaders().get(key) == ColumnType.STRING) {
						cell.setCellValue((String) rowData.getCellValue(key));
						cell.setCellStyle(valueStringTableStyle);
					}
					if(this.report.getCollection().getHeaders().get(key) == ColumnType.LOCALDATE) {
						LocalDate localDate = (LocalDate) rowData.getCellValue(key);
						cell.setCellValue(localDate.format(FORMATO_FECHA));
						cell.setCellStyle(valueStringTableStyle);
					}
					if(this.report.getCollection().getHeaders().get(key) == ColumnType.LOCALDATETIME) {
						LocalDate localDate = (LocalDate) rowData.getCellValue(key);
						cell.setCellValue(localDate.format(FORMATO_FECHA_HORA));
						cell.setCellStyle(valueStringTableStyle);
					}
					numCol++;
				}
				rowNum ++;
				numCol = 1;
			}
			
			for(int i = 0; i < 100; i++) {
	            sheet.autoSizeColumn(i);
	        }
			book.write(out);
			this.excelData = new ByteArrayInputStream(out.toByteArray());
			out.close();
		} catch (Exception e) {
			logger.error("[ERROR] build", e);
		}
	}

	@Override
	public ByteArrayInputStream getReult() {
		return this.excelData;
	}

	

}
