package pe.confianza.colaboradores.gcontenidos.server.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.BoletaModel;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
