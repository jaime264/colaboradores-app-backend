package pe.confianza.colaboradores.gcontenidos.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/vacaciones/reprogramacion")
@Api(value = "Reprogramación de vacaciones API REST", description = "Operaciones con reprogramación de vacaciones")
public class VacacionesReprogramacionController {
	
	private static Logger logger = LoggerFactory.getLogger(VacacionesReprogramacionController.class);
	
	

}
