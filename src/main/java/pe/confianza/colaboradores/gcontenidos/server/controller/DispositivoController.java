package pe.confianza.colaboradores.gcontenidos.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Dispositivo;
import pe.confianza.colaboradores.gcontenidos.server.service.DispositivoService;
import pe.confianza.colaboradores.gcontenidos.server.util.SecurityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class DispositivoController {

    @Autowired
    private DispositivoService deviceService;

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
    @PostMapping("/dispositivo/add")
    public ResponseEntity<?> addDevice(@RequestBody Dispositivo device) {
        ResponseStatus responseStatus = new ResponseStatus();
        List<Dispositivo> devices = new ArrayList<Dispositivo>();

        try {
			//Validamos usuario de la sesión
			SecurityUtils.validateUserSession(device.getUsuario());

            Optional<Dispositivo> dispositivo = deviceService.findByUser(device.getUsuario());
            if (dispositivo == null || !dispositivo.isPresent()) {
                devices.add(device);
			} else {
                Dispositivo newDevice = new Dispositivo();
                newDevice.set_id(dispositivo.get().get_id());
                newDevice.setUsuario(device.getUsuario());
                newDevice.setIdDispositivo(dispositivo.get().getIdDispositivo());
                newDevice.setIdDispositivoFirebase(dispositivo.get().getIdDispositivoFirebase());
                if (device.getIdDispositivo() != null)
                    newDevice.setIdDispositivo(device.getIdDispositivo());
                if (device.getIdDispositivoFirebase() != null)
                    newDevice.setIdDispositivoFirebase(device.getIdDispositivoFirebase());
                devices.add(newDevice);
			}
			responseStatus = deviceService.createDevice(devices);
		} catch (Exception e) {
			if (e.getMessage().equals(SecurityUtils.UNAUTHORIZED)) {
				responseStatus.setCodeStatus(99);
				responseStatus.setMsgStatus("Usuario de sesión no concuerda con el de consulta");
				responseStatus.setResultObj("{}");
				return new ResponseEntity<>(responseStatus, HttpStatus.UNAUTHORIZED);
			} else {
				responseStatus.setCodeStatus(99);
				responseStatus.setMsgStatus("Error al registrar en la base de datos: " + e.getMessage());
				responseStatus.setResultObj("{}");
				return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
			}
        }
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

}
