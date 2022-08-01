package pe.confianza.colaboradores.gcontenidos.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Nivel1;
import pe.confianza.colaboradores.gcontenidos.server.service.Nivel1Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class Nivel1Controller {

    @Autowired
    private Nivel1Service nivel1Service;

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
    @PostMapping("/nivel1")
    public ResponseEntity<?> show() {
        List<Nivel1> lstNivel1 = null;
        Map<String, Object> response = new HashMap<>();

        try {
			lstNivel1 = nivel1Service.findAll();
            if (lstNivel1 == null) {
                response.put("mensaje", "nivel 1 no existe en la base de datos!");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(lstNivel1, HttpStatus.OK);
    }

}
