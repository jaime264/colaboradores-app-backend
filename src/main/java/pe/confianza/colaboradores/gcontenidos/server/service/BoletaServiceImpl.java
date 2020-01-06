package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.BoletaModel;

@Service("boletaService")
public class BoletaServiceImpl implements BoletaService {

	@SuppressWarnings("deprecation")
	@Override
	public JasperPrint generarPDF(HttpServletRequest httpServletRequest, List<BoletaModel> lstDetBoleta) {

		try {
			System.out.println("Antes de jasper");
			List<BoletaModel> lstDetaBoletaPrin = new ArrayList<>();
			lstDetaBoletaPrin.add(lstDetBoleta.get(0));

			Map<String, Object> parameters = new HashMap<String, Object>();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstDetaBoletaPrin);
			parameters.put("SRBoletaModel", lstDetBoleta);

			JasperReport report;
				report = JasperCompileManager
						.compileReport(httpServletRequest.getRealPath("/") + "static\\report\\BoletaDesign.jrxml");
			
			return JasperFillManager.fillReport(report, parameters, dataSource);
			} catch (JRException e) {
				e.printStackTrace();
			}
		return null;
	}

}
