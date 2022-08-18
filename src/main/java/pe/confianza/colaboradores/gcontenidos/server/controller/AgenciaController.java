package pe.confianza.colaboradores.gcontenidos.server.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.negocio.AgenciaNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api/agencias")
@Api(value = "Agencias API REST Endpoint", description = "Operaciones con agencias")
public class AgenciaController {
	
	@Autowired
	private AgenciaNegocio agenciaNegocio;
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de territorios", value = "url proxy /agencias/territorios")
	@PostMapping("/territorios")
	public ResponseEntity<ResponseStatus> listarTerritorios(@Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(agenciaNegocio.listarTerritorios(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de corredores", value = "url proxy /agencias/corredores")
	@PostMapping("/corredores/{codigo-territorio}")
	public ResponseEntity<ResponseStatus> listarCorredores(@PathVariable("codigo-territorio") String codigoTerritorio, @Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(agenciaNegocio.listarCorredores(codigoTerritorio, peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de agencias", value = "url proxy /agencias")
	@PostMapping("/{codigo-corredor}")
	public ResponseEntity<ResponseStatus> listarAgencias(@PathVariable("codigo-corredor") String codigoCorredor, @Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(agenciaNegocio.listarAgencias(codigoCorredor, peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

}
