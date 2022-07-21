package pe.confianza.colaboradores.gcontenidos.server.util.file.write;

public class ReportFactory {
	
	public IReport createReport(Report report) {
		if(report.getType() == null)
			throw new RuntimeException("No se especificó tipo de reporte");
		
		return null;
	}

}
