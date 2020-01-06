package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperPrint;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.BoletaModel;

public interface BoletaService {
	
	JasperPrint generarPDF(HttpServletRequest httpServletRequest, List<BoletaModel> lstDetBoleta);


}
