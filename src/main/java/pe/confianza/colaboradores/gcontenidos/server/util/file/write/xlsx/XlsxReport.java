package pe.confianza.colaboradores.gcontenidos.server.util.file.write.xlsx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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

import pe.confianza.colaboradores.gcontenidos.server.util.file.write.IReport;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.Report;

public class XlsxReport implements IReport<ByteArrayInputStream> {
	
	private Report report;
	
	public XlsxReport(Report report) {
		this.report = report;
	}

	@Override
	public ByteArrayInputStream generateReport() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Workbook  book = new XSSFWorkbook();
		try {
			byte[] logoBytes = null;
			if(this.report.getLogo() != null) {
				int pictureureIdx  = book.addPicture(logoBytes, Workbook.PICTURE_TYPE_PNG);
				this.report.getLogo().close();
			}
			
			CreationHelper createHelper = book.getCreationHelper();
			Sheet sheet = book.createSheet("financiera_confianza");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	

}
