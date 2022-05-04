package pe.confianza.colaboradores.gcontenidos.server.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.dao.ParametrosDao;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.ParametroMapper;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Parametro;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametrosConstants;

@Service
public class ParametrosServiceImpl implements ParametrosService {

	@Autowired
	private ParametrosDao parametrosDao;

	@Autowired
	private ParametrosConstants parametros;

	@Override
	public List<Parametro> listParams() {
		return parametrosDao.findAll();
	}

	@Override
	public ResponseParametro registrar(RequestParametro request) {
		Optional<Parametro> parametroExiste = parametrosDao.findOneByCodigo(request.getCodigo());
		if (!parametroExiste.isPresent()) {
			Parametro parametro = ParametroMapper.convert(request);
			parametro.setFechaRegistro(new SimpleDateFormat("dd/mm/yyyy").format(new Date()));
			parametro.setEstado(1);
			parametro = parametrosDao.save(parametro);
			return ParametroMapper.convert(parametro);
		}
		throw new AppException("Ya existe un parametro con el código " + request.getCodigo());
	}

	@Override
	public Parametro buscarPorCodigo(String codigo) {
		return parametrosDao.findOneByCodigo(codigo).get();
	}

	@Override
	public ResponseParametro buscarPorId(long id) {

		String value = parametros.FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES;
		
		Parametro parametro = parametrosDao.findById(id).get();
		if (parametro == null)
			throw new ModelNotFoundException("No existe parámetro con id " + id);
		return ParametroMapper.convert(parametro);
	}

	@Override
	public String buscarValorPorCodigo(String codigo) {
		return parametrosDao.findOneByCodigo(codigo).get().getValor();

	}

}
