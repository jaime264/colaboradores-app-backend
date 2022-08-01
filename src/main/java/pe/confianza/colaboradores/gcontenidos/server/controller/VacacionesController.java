package pe.confianza.colaboradores.gcontenidos.server.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Vacacion;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.SecurityUtils;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Api(value = "Vacaciones API REST Endpoint", description = "Operaciones con vacaciones")
public class VacacionesController {

    private static final Logger logger = LoggerFactory.getLogger(VacacionesController.class);

    @Autowired
    private VacacionService vacacionService;

    @Autowired
    private AuditoriaService auditoriaService;

    @SuppressWarnings("resource")
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
    @PostMapping("/vacaciones/upload/{fechaCorte}")
    public ResponseStatus uploadExcel(@RequestParam("file") MultipartFile reapExcelDataFile, @PathVariable("fechaCorte") String fechaCorte) throws IOException, JRException {
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        fechaCorte = fechaCorte.replace("-", "/");
        return vacacionService.importExcel(worksheet, fechaCorte);
    }

    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
    @PostMapping("/vacaciones")
    public Vacacion showVacationByUser(@RequestBody Vacacion vacacion) {
        Vacacion vacacionOut = null;
        logger.info("Empleado: " + vacacion.getCodigoSpring());
        Gson gson = new Gson();
		String jsonData = gson.toJson(vacacion);
        try {
			//Validamos usuario de la sesión
			SecurityUtils.validateUserSession(vacacion.getUsuarioBT());

            vacacionOut = vacacionService.showVacationByUser(vacacion.getCodigoSpring());
            if (vacacionOut != null) {
                auditoriaService.createAuditoria("002", "006", 0, "OK", BsonDocument.parse(jsonData));
            } else {
                logger.error("Error al obtener datos de vacaciones: null");
                auditoriaService.createAuditoria("002", "006", 99, "Error al obtener datos de vacaciones: null", BsonDocument.parse(jsonData));
            }
        } catch (Exception e) {
            logger.error("Error al obtener datos de vacaciones: " + e.getMessage());
            auditoriaService.createAuditoria("002", "006", 99, "Error al obtener datos de vacaciones: " + e.getMessage(), BsonDocument.parse(jsonData));
        }
        return vacacionOut;
    }


}
