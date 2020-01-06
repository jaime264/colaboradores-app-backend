package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsBoleta;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.BoletaModel;
import pe.confianza.colaboradores.gcontenidos.server.service.BoletaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200" })
public class BoletaController {

	@Autowired
	private BoletaService _boletaService;
	
	@Value("${url.spring.rest.boleta}")
	private String urlBoleta;

	@Value("${compania.id}")
	private String idCompania;
	
	@RequestMapping("/boleta/{empleado}/{periodo}")
	public void obtenerBoleta(HttpServletRequest httpServletRequest, HttpServletResponse response,
			@PathVariable("empleado") int idEmpleado, @PathVariable("periodo") String periodo) throws IOException, JRException {

		String bodyResponse = StringUtils.EMPTY;
		String readLine = StringUtils.EMPTY;

		StringBuilder url = new StringBuilder(urlBoleta);
		
		URL urlForGetRequest = new URL(url.toString());
		
		ParamsBoleta params = new ParamsBoleta();
		params.setIdCompania(idCompania);
		params.setIdEmpleado(idEmpleado);
		params.setPeriodo(periodo);
		String paramsOut = new Gson().toJson(params);
		
		HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		conection.setDoOutput(true);
		conection.setRequestMethod("POST");
		conection.setRequestProperty("Content-Type", "application/json");
		OutputStream os = conection.getOutputStream();
		
		os.write(paramsOut.getBytes());
		os.flush();
		os.close();

		int responseCode = conection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			StringBuffer responsePrint = new StringBuffer();
			while ((readLine = in.readLine()) != null) {
				responsePrint.append(readLine);
			}
			in.close();

			bodyResponse = responsePrint.toString();

			Type founderListType = new TypeToken<List<BoletaModel>>() { }.getType();

			List<BoletaModel> lstDetBoleta = new Gson().fromJson(bodyResponse, founderListType);

			/**
			 * GENERANDO BOLETA EN PDF 
			 */
			
			JasperPrint materiReport = _boletaService.generarPDF(httpServletRequest, lstDetBoleta);
			response.setContentType("application/x-pdf");
			response.setHeader("Content-disposition", "attachment; filename=boleta_.pdf");
			JasperExportManager.exportReportToPdfStream(materiReport, response.getOutputStream());
			
		}
	}
}
