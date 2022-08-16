package pe.confianza.colaboradores.gcontenidos.server.service;

import net.sf.jasperreports.engine.JasperPrint;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.BoletaModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BoletaService {
	
	JasperPrint generarPDF(HttpServletRequest httpServletRequest, List<BoletaModel> lstDetBoleta);


}
