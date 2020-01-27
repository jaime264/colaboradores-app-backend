package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Dispositivo;
import pe.confianza.colaboradores.gcontenidos.server.service.DispositivoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class DispositivoController {
	
	@Autowired
	private DispositivoService deviceService;
	
	@PostMapping("/dispositivo/add")
	public ResponseEntity<?> addDevice(@RequestBody Dispositivo device) {
		ResponseStatus responseStatus = null;
		List<Dispositivo> devices = new ArrayList<Dispositivo>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			Optional<Dispositivo> dispositivo = deviceService.findByUser(device.getUsuario());
			if (dispositivo == null || !dispositivo.isPresent()) {
				devices.add(device);
				responseStatus = deviceService.createDevice(devices);
			} else {
				Dispositivo newDevice = new Dispositivo();
				newDevice.set_id(dispositivo.get().get_id());
				newDevice.setUsuario(device.getUsuario());
				newDevice.setIdDispositivo(device.getIdDispositivo());
				devices.add(newDevice);
				responseStatus = deviceService.createDevice(devices);
			}
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al registrar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
	}

}
