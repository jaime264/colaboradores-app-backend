package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.dao.DispositivoDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Dispositivo;

@Service
public class DispositivoServiceImpl implements DispositivoService {
	
	@Autowired 
	private DispositivoDao deviceDao;

	@Override
	public ResponseStatus createDevice(List<Dispositivo> devices) {
		List<Dispositivo> devicesOut = new ArrayList<Dispositivo>();
		ResponseStatus status = new ResponseStatus();
		try {
			devicesOut = deviceDao.saveAll(devices);
			if (devicesOut.size() > 0) {
				status.setCodeStatus(0);
				status.setMsgStatus("Registro creado correctamente");
				status.setResultObj(devicesOut.get(0).get_id());
			} else {
				status.setCodeStatus(99);
				status.setMsgStatus("No se pudo registrar la publicación");
			}
		} catch (Exception ex) {
			status.setCodeStatus(99);
			status.setMsgStatus(ex.getMessage());
		}
		return status;
	}
	
	@Override
	public Optional<Dispositivo> findByUser(String user) {		
		return deviceDao.findByUser(user);
	}

}
