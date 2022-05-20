package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.BoletaModel;

@Service("boletaService")
public class BoletaServiceImpl implements BoletaService {

	@Value("${nombre.jasper}")
	private String nombreJasper;
	
	@Value("${ruta.jasper}")
	private String urlJasper;
	
	@Override
	public JasperPrint generarPDF(HttpServletRequest httpServletRequest, List<BoletaModel> lstDetBoleta) {

		try {
			List<BoletaModel> lstDetaBoletaPrin = new ArrayList<>();
			lstDetaBoletaPrin.add(lstDetBoleta.get(0));

			Map<String, Object> parameters = new HashMap<String, Object>();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstDetaBoletaPrin);
			parameters.put("SRBoletaModel", lstDetBoleta);

			JasperReport report;
				report = JasperCompileManager.compileReport(urlJasper + nombreJasper);
			
			return JasperFillManager.fillReport(report, parameters, dataSource);
			} catch (JRException e) {
				e.printStackTrace();
			}
		return null;
	}

}
