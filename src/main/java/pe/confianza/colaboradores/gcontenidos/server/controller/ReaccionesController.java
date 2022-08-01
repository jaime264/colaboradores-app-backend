package pe.confianza.colaboradores.gcontenidos.server.controller;

import com.google.gson.Gson;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReaccion;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Reaccion;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.ReaccionService;
import pe.confianza.colaboradores.gcontenidos.server.util.SecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class ReaccionesController {

    private static final Logger logger = LoggerFactory.getLogger(BoletaController.class);

    @Autowired
    private ReaccionService reaccionService;

    @Autowired
    private AuditoriaService auditoriaService;

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
    @PostMapping("/reacciones/list")
    public ResponseEntity<?> show(@RequestBody RequestReaccion requestReaccion) {
        List<Reaccion> lstReacciones = null;
        Map<String, Object> response = new HashMap<>();
        Gson gson = new Gson();

        try {
			//Validamos usuario de la sesión
			SecurityUtils.validateUserSession(requestReaccion.getLogAuditoria().getUsuario());
            //Validamos usuario de la sesión
            if (requestReaccion.getLogAuditoria() != null) {
                if (requestReaccion.getLogAuditoria().getUsuario() != null) {
                    SecurityUtils.validateUserSession(requestReaccion.getLogAuditoria().getUsuario());
                } else {
                    response.put("mensaje", "Usuario No Autorizado");
                    response.put("error", "Auditoria: Usuario No Autorizado");
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }
            } else {
                response.put("mensaje", "Usuario No Autorizado");
                response.put("error", "Es necesario los datos de auditoria");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            lstReacciones = reaccionService.listReacciones();
            if (lstReacciones == null) {
                String mensaje = "Reacciones no existen en la base de datos!";
                String jsonData = gson.toJson(requestReaccion);
                auditoriaService.createAuditoria("002", "012", 99, mensaje, BsonDocument.parse(jsonData));
                response.put("mensaje", mensaje);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                String jsonData = gson.toJson(requestReaccion);
                auditoriaService.createAuditoria("002", "012", 0, "OK", BsonDocument.parse(jsonData));
            }
        } catch (Exception e) {
            String mensaje = "Error al realizar la consulta en la base de datos";
            String jsonData = gson.toJson(requestReaccion);
            logger.error(mensaje, e.getMessage());
            auditoriaService.createAuditoria("002", "012", 99, mensaje + ": " + e.getMessage(), BsonDocument.parse(jsonData));
			if (e.getMessage().equals(SecurityUtils.UNAUTHORIZED)) {
				response.put("mensaje", "Usuario No Autorizado");
				response.put("error", "Usuario de sesión no concuerda con el de consulta");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			} else {
				response.put("mensaje", mensaje);
				response.put("error", e.getMessage());
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
        }
        return new ResponseEntity<>(lstReacciones, HttpStatus.OK);
    }

}
