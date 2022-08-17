package pe.confianza.colaboradores.gcontenidos.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;

@RestController
@RequestMapping("/api/gastos")
@Api(value = "Gastos API REST Endpoint", description = "Operaciones con gastos")
public class GastosController {
	
	@Autowired
	private GestionarPresupuestoNegocio gestionarPresupuestoNegocio;

}
