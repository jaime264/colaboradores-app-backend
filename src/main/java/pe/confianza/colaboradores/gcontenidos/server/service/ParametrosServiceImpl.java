package pe.confianza.colaboradores.gcontenidos.server.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.dao.ParametrosDao;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.ParametroMapper;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Parametro;

@Service
public class ParametrosServiceImpl implements ParametrosService {
	
	@Autowired 
	private ParametrosDao parametrosDao;
	
	@Override
	public List<Parametro> listParams() {
		return parametrosDao.findAll();
	}
	
	
	@Override
	public ResponseParametro registrar(RequestParametro request) {
		Parametro parametroExiste = parametrosDao.findParametroByCodigo(request.getCodigo());
		if(parametroExiste != null) {
			Parametro parametro =  ParametroMapper.convert(request);
			parametro.setId(Instant.now().toEpochMilli());
			parametro.setFechaRegistro(new SimpleDateFormat("dd/mm/yyyy").format(new Date()));
			parametro.setEstado(1);
			parametro = parametrosDao.save(parametro);
			return ParametroMapper.convert(parametro);
		}
		throw new AppException("Ya existe un parametro con el código " + request.getCodigo());
	}


	@Override
	public Parametro buscarPorCodigo(String codigo) {
		return parametrosDao.findParametroByCodigo(codigo);
	}


	@Override
	public ResponseParametro buscarPorId(long id) {
		Parametro parametro = parametrosDao.findParametroById(id);
		if(parametro == null)
			throw new ModelNotFoundException("No existe parámetro con id " + id);
		return ParametroMapper.convert(parametro);
	}
	
	

}
