package pe.confianza.colaboradores.gcontenidos.server.service;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.RequestParametroActualizacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestModificarMetaVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseAcceso;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseEmpleadoMeta;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametroTipo;
import pe.confianza.colaboradores.gcontenidos.server.controller.RequestFiltroEmpleadoMeta;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.ParametroMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.FuncionalidadApp;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametroUnidad;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoParametro;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;
import pe.confianza.colaboradores.gcontenidos.server.util.VacacionesSubTipoParametro;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ProgramacionVacacionNegocio;

@Service
public class ParametrosServiceImpl implements ParametrosService {
	
	private static Logger logger = LoggerFactory.getLogger(ParametrosServiceImpl.class);

	@Autowired
	private CargaParametros parametrosConstants;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private VacacionMetaResumenService vacacionMetaResumenService;
	
	@Autowired
	private ProgramacionVacacionNegocio ProgramacionVacacionNegocio;
	
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
			List<ResponseAcceso> accesos = empleadoService.consultaAccesos(parametro.getUsuarioOperacion());
			boolean tienePermiso = false;
			for (ResponseAcceso acceso : accesos) {
				if(FuncionalidadApp.PARAMETRIA_VACACIONES.codigo.equals(acceso.getFuncionalidadCodigo()) && acceso.isModificar()) {
					tienePermiso = true;
				}
			}
			if(!tienePermiso)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.parametros.sin_permiso", parametro.getUsuarioOperacion()));
			Parametro parametroOld = parametrosConstants.search(parametro.getCodigo());
			if(parametroOld == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "vacaciones.parametros.no_encontrado", parametro.getCodigo()));
			ParametroUnidad unidad = ParametroUnidad.buscar(parametroOld.getUnidad());
			if(unidad == null)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"));
			if(!ParametroUnidad.esValidoValor(unidad, parametro.getNuevoValor()))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.parametros.formato_incorrecto"));
			parametro.setNuevoValor(ParametroUnidad.procesarNuevoValor(unidad, parametro.getNuevoValor()));
			String descripcionParametro = null;
			if(ParametroUnidad.ESTADO_VACACION.codigo.equals(unidad.codigo)) {
				String[] valorArray = parametro.getNuevoValor().split("-");
				descripcionParametro = valorArray[0];
			}
			Parametro parametroNuevo = parametrosConstants.actualizarParametro(parametro.getCodigo(), parametro.getNuevoValor(),
					descripcionParametro, parametro.getUsuarioOperacion());
			if(parametroNuevo == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "vacaciones.parametros.no_encontrado", parametro.getCodigo()));
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

	@Override
	public List<ResponseParametroTipo> listaParametrovaccionesTipos() {
		List<ResponseParametroTipo> tipos = Arrays.asList(VacacionesSubTipoParametro.values()).stream()
				.map(t -> {
					ResponseParametroTipo response = new ResponseParametroTipo();
					response.setCodigo(t.codigo);
					response.setDescripcion(t.descripcion);
					return response;
				}).collect(Collectors.toList());
		return tipos;
	}

	@Override
	public Page<ResponseEmpleadoMeta> listarVacacionMeta(RequestFiltroEmpleadoMeta filtro) {
		logger.info("[BEGIN] listarVacacionMeta");
		if(filtro.getIdPuesto() != null) {
			return vacacionMetaResumenService.listarPorPuesto(filtro);
		}
		
		if(filtro.getNombre() != null) {
			return vacacionMetaResumenService.listarPorNombreEmpleado(filtro);
		}
		filtro.setNombre("");
		return vacacionMetaResumenService.listarPorNombreEmpleado(filtro);
	}

	@Override
	public void actualizarMeta(RequestModificarMetaVacacion actualizacion) {
		try {
			List<ResponseAcceso> accesos = empleadoService.consultaAccesos(actualizacion.getUsuarioOperacion());
			boolean tienePermiso = false;
			for (ResponseAcceso acceso : accesos) {
				if(FuncionalidadApp.PARAMETRIA_VACACIONES.codigo.equals(acceso.getFuncionalidadCodigo()) && acceso.isModificar()) {
					tienePermiso = true;
				}
			}
			if(!tienePermiso)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.parametros.sin_permiso", actualizacion.getUsuarioOperacion()));
			LocalDate fechaActual = LocalDate.now();
			if(fechaActual.isAfter(parametrosConstants.getFechaMaximaAprobacionProgramaciones()))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.parametros.meta.fuera_fecha"));
			ProgramacionVacacionNegocio.actualizarMeta(actualizacion.getIdMeta(), actualizacion.getNuevaMeta(), actualizacion.getUsuarioOperacion().trim());
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
