package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import net.sf.jasperreports.engine.JRException;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Vacacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class VacacionesController {
	
	private static Logger logger = LoggerFactory.getLogger(VacacionesController.class);

	@Autowired
	private VacacionService vacacionService;
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;

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
	
	
	@PostMapping("/vacaciones/registro-programacion")
	public ResponseEntity<ResponseStatus> registroProgramacion(@Valid @RequestBody RequestProgramacionVacacion programacionRequest) {
		logger.info("Empleado: " + programacionRequest.getCodigoSpring());
		Gson gson = new Gson();
		String jsonData = gson.toJson(programacionRequest);
		ResponseStatus responseStatus = new ResponseStatus();;
		try {
			VacacionProgramacion programacion = vacacionProgramacionService.registroSolicitud(programacionRequest);
			auditoriaService.createAuditoria("002", "006", Constantes.COD_OK, Constantes.OK, BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			responseStatus.setResultObj(programacion);
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			auditoriaService.createAuditoria("002", "006", Constantes.COD_ERR, "Error al registrar programación de vacaciones: " + e.getMessage(), BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
