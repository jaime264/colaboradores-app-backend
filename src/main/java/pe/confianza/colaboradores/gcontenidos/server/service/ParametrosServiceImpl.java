package pe.confianza.colaboradores.gcontenidos.server.service;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.RequestParametroActualizacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.ParametroMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoParametro;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class ParametrosServiceImpl implements ParametrosService {
	
	private static Logger logger = LoggerFactory.getLogger(ParametrosServiceImpl.class);

	@Autowired
	private CargaParametros parametrosConstants;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public List<Parametro> listParams() {
		return parametrosConstants.findAll();
	}

	@Override
	public ResponseParametro registrar(RequestParametro request) {
		Parametro parametro = parametrosConstants.addParametro(request);
		return ParametroMapper.convert(parametro);
	}

	@Override
	public Parametro buscarPorCodigo(String codigo) {
		return parametrosConstants.search(codigo);
	}

	@Override
	public ResponseParametro buscarPorId(long id) {		
		Parametro parametro = parametrosConstants.search(id);
		if (parametro == null)
			throw new ModelNotFoundException("No existe parámetro con id " + id);
		return ParametroMapper.convert(parametro);
	}

	@Override
	public String buscarValorPorCodigo(String codigo) {
		return buscarPorCodigo(codigo).getValor();
	}

	@Override
	public List<ResponseParametro> listarParametrosVacacionesPorTipo(String codigoTipo) {
		try {
			TipoParametro tipoVacaciones = TipoParametro.VACACION;
			return parametrosConstants.listarPorTipoYSubTipo(tipoVacaciones.codigo, codigoTipo).stream()
					.map(p -> {
						return ParametroMapper.convert(p);
					}).collect(Collectors.toList());
		} catch (Exception e) {
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
		
	}

	@Override
	public ResponseParametro actualizarParametroVacaciones(RequestParametroActualizacion parametro) {
		try {
			Parametro parametroOld = parametrosConstants.search(parametro.getCodigo());
			if(parametroOld == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "vacaciones.paramtros.no_encontrado", parametro.getCodigo()));
			Parametro parametroNuevo = parametrosConstants.actualizarParametro(parametro.getCodigo(), parametro.getNuevoValor(), parametro.getUsuarioOperacion());
			if(parametroNuevo == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "vacaciones.paramtros.no_encontrado", parametro.getCodigo()));
			return ParametroMapper.convert(parametroNuevo);
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] actualizarParametroVacaciones", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			logger.error("[ERROR] actualizarParametroVacaciones", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] actualizarParametroVacaciones", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

}
