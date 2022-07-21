package pe.confianza.colaboradores.gcontenidos.server.util.file.write;

import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.util.file.write.xlsx.XlsxReport;

@Component
public class ReportFactory {
	
	public IReport createReport(Report report) {
		if(report.getType() == null)
			throw new RuntimeException("No se especificó tipo de reporte");
		if(report.getType().equals("XLSX"))
			return new XlsxReport(report);
		throw new RuntimeException("Tipo no válido");
	}

}
