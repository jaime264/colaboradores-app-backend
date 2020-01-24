package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsBoleta;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestBoleta;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.BoletaModel;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.BoletaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445" })
public class BoletaController {
	
	private static Logger logger = LoggerFactory.getLogger(BoletaController.class);

	@Autowired
	private BoletaService _boletaService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@Value("${url.spring.rest.boleta}")
	private String urlBoleta;

	@Value("${compania.id}")
	private String idCompania;
	 
	@RequestMapping("/boleta")
	public void obtenerBoleta(HttpServletRequest httpServletRequest, HttpServletResponse response,@RequestBody RequestBoleta requestBoleta
			) throws IOException, JRException {
		Gson gson = new Gson();
		try {
			String bodyResponse = StringUtils.EMPTY;
			String readLine = StringUtils.EMPTY;

			StringBuilder url = new StringBuilder(urlBoleta);
			
			URL urlForGetRequest = new URL(url.toString());
			
			ParamsBoleta params = new ParamsBoleta();
			params.setIdCompania(idCompania);
			params.setIdEmpleado(requestBoleta.getEmpleado());
			params.setPeriodo(requestBoleta.getPeriodo());
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
				response.setHeader("Content-disposition", "attachment; filename=BOLETA_" +requestBoleta.getEmpleado() + "_" + requestBoleta.getPeriodo() + ".pdf");
				JasperExportManager.exportReportToPdfStream(materiReport, response.getOutputStream());
				
				/**
				 * GUARDANDO AUDITORIA
				 */
				
				String jsonData = gson.toJson(requestBoleta);
				auditoriaService.createAuditoria("002", "005", 0, "OK", BsonDocument.parse(jsonData));
			}
		} catch (Exception e) {
			String jsonData = gson.toJson(requestBoleta);
			auditoriaService.createAuditoria("002", "005", 99, "Error al obtener boleta: " + e.getMessage(), BsonDocument.parse(jsonData));
		}
	}
	
	@RequestMapping("/boleta/base64")
	public ResponseEntity<?> obtenerBoletaString(HttpServletRequest httpServletRequest, HttpServletResponse response,@RequestBody RequestBoleta requestBoleta
			) throws IOException, JRException {
		Gson gson = new Gson();
		ResponseStatus responseStatus = new ResponseStatus();
		String base64  ="";
		try {
			logger.info("Empleado: " + requestBoleta.getEmpleado());
			String bodyResponse = StringUtils.EMPTY;
			String readLine = StringUtils.EMPTY;

			StringBuilder url = new StringBuilder(urlBoleta);
			
			URL urlForGetRequest = new URL(url.toString());
			
			ParamsBoleta params = new ParamsBoleta();
			params.setIdCompania(idCompania);
			params.setIdEmpleado(requestBoleta.getEmpleado());
			params.setPeriodo(requestBoleta.getPeriodo());
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
				byte[] output =  JasperExportManager.exportReportToPdf(materiReport);
				base64 = Base64.getEncoder().encodeToString(output);
				responseStatus.setCodeStatus(0);
				responseStatus.setMsgStatus("Registro creado correctamente");
				responseStatus.setResultObj(base64);
				/**
				 * GUARDANDO AUDITORIA
				 */
				
				String jsonData = gson.toJson(requestBoleta);
				auditoriaService.createAuditoria("002", "005", 0, "OK", BsonDocument.parse(jsonData));
			}
		} catch (Exception e) {
			logger.error("Error al obtener boleta: " + e.getMessage());
			String jsonData = gson.toJson(requestBoleta);
			auditoriaService.createAuditoria("002", "005", 99, "Error al obtener boleta: " + e.getMessage(), BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(99);
			responseStatus.setMsgStatus("Error al obtener boleta: [" + e.getMessage() + "]");
			responseStatus.setResultObj(null);
		}
		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
	}
}
