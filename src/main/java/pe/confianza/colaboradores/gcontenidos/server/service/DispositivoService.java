package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Dispositivo;

import java.util.List;
import java.util.Optional;

public interface DispositivoService {

	ResponseStatus createDevice(List<Dispositivo> devices);
	
	public Optional<Dispositivo> findByUser(String user);

}
