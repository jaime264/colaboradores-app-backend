package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroPuesto;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePuesto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PuestoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Puesto;

@Service
public class PuestoServiceImpl implements PuestoService {
	
	@Autowired
	private PuestoDao dao;

	@Override
	public List<ResponsePuesto> consultar(RequestFiltroPuesto filtro) {
		List<Puesto> puestos = dao.listarPuestos(filtro.getFiltro().toUpperCase().trim());
		puestos = puestos == null ? new ArrayList<>() : puestos;
		return puestos.stream().map(p -> {
			ResponsePuesto response = new ResponsePuesto();
			response.setId(p.getId());
			response.setDescripcion(p.getDescripcion().toUpperCase());
			return response;
		}).collect(Collectors.toList());
	}

}
