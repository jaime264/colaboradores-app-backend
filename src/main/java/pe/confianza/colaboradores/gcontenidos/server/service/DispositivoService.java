package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Dispositivo;

public interface DispositivoService {

	ResponseStatus createDevice(List<Dispositivo> devices);
	
	public Optional<Dispositivo> findByUser(String user);

}
