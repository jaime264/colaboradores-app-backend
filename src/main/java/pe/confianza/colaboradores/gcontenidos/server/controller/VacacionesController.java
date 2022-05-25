package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import io.swagger.annotations.Api;
import net.sf.jasperreports.engine.JRException;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Vacacion;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionService;

@RestController
@RequestMapping("/api")
@Api(value = "Vacaciones API REST Endpoint", description = "Operaciones con vacaciones")
public class VacacionesController {
	
	private static Logger logger = LoggerFactory.getLogger(VacacionesController.class);

	@Autowired
	private VacacionService vacacionService;

	@Autowired
	private AuditoriaService auditoriaService;
	
	
	
	@SuppressWarnings("resource")
	@PostMapping("/vacaciones/upload/{fechaCorte}")
	public ResponseStatus uploadExcel(@RequestParam("file") MultipartFile reapExcelDataFile, @PathVariable("fechaCorte") String fechaCorte) throws IOException, JRException {
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		
		fechaCorte = fechaCorte.replace("-", "/");
		return vacacionService.importExcel(worksheet, fechaCorte);
	}
	
	
	@PostMapping("/vacaciones")
	public Vacacion showVacationByUser(@RequestBody Vacacion vacacion) throws IOException, JRException {
		Vacacion vacacionOut = null;
		logger.info("Empleado: " + vacacion.getCodigoSpring());
		Gson gson = new Gson();
		String jsonData = gson.toJson(vacacion);
		try {
			vacacionOut =  vacacionService.showVacationByUser(vacacion.getCodigoSpring());
			if(vacacionOut != null) {
				auditoriaService.createAuditoria("002", "006", 0, "OK", BsonDocument.parse(jsonData));
			}else {
				logger.error("Error al obtener datos de vacaciones: null");
				auditoriaService.createAuditoria("002", "006", 99, "Error al obtener datos de vacaciones: null", BsonDocument.parse(jsonData));
			}
		} catch(Exception e) {
			logger.error("Error al obtener datos de vacaciones: " + e.getMessage());
			auditoriaService.createAuditoria("002", "006", 99, "Error al obtener datos de vacaciones: " + e.getMessage(), BsonDocument.parse(jsonData));
		}
		return vacacionOut;
	}
	
	
}
