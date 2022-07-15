package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmpleadoRes;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.CumpleanosRes;
import pe.confianza.colaboradores.gcontenidos.server.api.spring.EmpleadoApi;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseAcceso;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseTerminosCondiciones;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.EmpleadoMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.AgenciaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.CorredorDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoAccesoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.FuncionalidadAccesoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PerfilStringAppDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PuestoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.TerritorioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.UnidadOperativaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Corredor;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.EmpleadoAcceso;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.FuncionalidadAcceso;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PerfilSpringApp;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Puesto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Territorio;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadOperativa;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.FuncionalidadApp;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

	private static Logger LOGGER = LoggerFactory.getLogger(EmpleadoServiceImpl.class);

	@Autowired
	private EmpleadoDao empleadoDao;

	@Autowired
	private PuestoDao puestoDao;

	@Autowired
	private AgenciaDao agenciaDao;

	@Autowired
	private EmpleadoApi empleadoApi;

	@Autowired
	private PeriodoVacacionService periodoVacacionService;

	@Autowired
	private VacacionMetaService vacacionMetaService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CorredorDao corredorDao;
	
	@Autowired
	private TerritorioDao territorioDao;
	
	@Autowired
	private UnidadOperativaDao unidadOperativaDao;
	
	@Autowired
	private PerfilStringAppDao perfilStringAppDao;
	
	@Autowired
	private FuncionalidadAccesoDao funcionalidadAccesoDao;
	
	@Autowired
	private EmpleadoAccesoDao empleadoAccesoDao;

	@Override
	public Empleado actualizarInformacionEmpleado(String usuarioBT) {
		LOGGER.info("[BEGIN] actualizarInformacionEmpleado {}", usuarioBT);
		EmpleadoRes empleadoSpring = empleadoApi.getPerfil(usuarioBT);
		LOGGER.info("Empleado: " + empleadoSpring.toString());
		if (!usuarioBT.trim().equalsIgnoreCase(empleadoSpring.getUsuarioBT().trim()))
			return null;
		Optional<Puesto> optPuesto = puestoDao.findOneByCodigo(empleadoSpring.getIdCargo());
		Optional<Agencia> optAgencia = agenciaDao.findOneByCodigo(empleadoSpring.getIdSucursal().trim());
		Optional<Empleado> optEmpleado = empleadoDao.findOneByUsuarioBT(empleadoSpring.getUsuarioBT().trim());
		Empleado empleado = EmpleadoMapper.convert(empleadoSpring);
		if (optEmpleado.isPresent()) {
			empleado.setId(optEmpleado.get().getId());
			empleado.setAceptaTerminosCondiciones(optEmpleado.get().isAceptaTerminosCondiciones());
		} else {
			empleado.setAceptaTerminosCondiciones(false);
		}
		empleado.setPuesto(optPuesto.isPresent() ? optPuesto.get() : null);
		empleado.setAgencia(optAgencia.isPresent() ? optAgencia.get() : null);
		empleado.setFechaModifica(LocalDateTime.now());
		empleado.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		empleado.setEstadoMigracion(EstadoMigracion.IMPORTADO.valor);
		empleado = empleadoDao.save(empleado);
		periodoVacacionService.actualizarPeriodos(empleado, usuarioBT);
		vacacionMetaService.consolidarMetaAnual(empleado, LocalDate.now().getYear() + 1, usuarioBT);
		LOGGER.info("[END] actualizarInformacionEmpleado");
		return empleado;
	}


	public Empleado listEmpleadoById(Long id) {

		return empleadoDao.findById(id).get();
	}

	@Override
	public List<Empleado> listar() {
		return empleadoDao.findAll();
	}

	@Override
	public Empleado buscarPorUsuarioBT(String usuarioBT) {
		LOGGER.info("[BEGIN] buscarPorUsuarioBT {}", usuarioBT);
		Optional<Empleado> optEmpleado = empleadoDao.findOneByUsuarioBT(usuarioBT.trim());
		if (!optEmpleado.isPresent()) {
			try {
				return actualizarInformacionEmpleado(usuarioBT);
			} catch (Exception e) {
				Empleado em = new Empleado();
				em.setId(null);
				return em;
			}

		} else {
			return optEmpleado.get();
		}
	}

	@Override
	public List<CumpleanosRes> findfechaNacimientoDeHoy() {
		
		List<Empleado> listEmp = new ArrayList<>();
		List<CumpleanosRes> listCumpleanos = new ArrayList<>();

		Instant instant = Instant.now();
		ZonedDateTime limaQuitoTime = instant.atZone(ZoneId.of("-05:00"));

		int mes = limaQuitoTime.getMonthValue();
		int dia = limaQuitoTime.getDayOfMonth();

		try {
			listEmp = empleadoDao.findfechaNacimientoDeHoy(mes, dia);
			
			listEmp.stream().forEach(e ->{
				CumpleanosRes cumpleanos = new CumpleanosRes();
				cumpleanos.setIdEmpleado(e.getId());
				cumpleanos.setNombres(e.getNombreCompleto());
				cumpleanos.setAgencia(e.getAgencia().getDescripcion());
				listCumpleanos.add(cumpleanos);
			});
			
		} catch (Exception e) {
			e.getMessage();
		}

		return listCumpleanos;
	}

	@Override
	public int obtenerCantidadEmpleadosPorPuestoYUnidadNegocio(long codigoUnidadNegocio, String descripcionPuesto) {
		return empleadoDao.obtenerCantidadEmpleadosPorPuestoYPorUnidadNegocio(codigoUnidadNegocio, descripcionPuesto + "%");
	}

	@Override
	public Empleado buscarPorCodigo(Long codigo) {
		// TODO Auto-generated method stub
		List<Empleado> emp = new ArrayList<>();
		try {
			emp = empleadoDao.findByCodigo(codigo);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return emp.get(0);
	}

	@Override
	public ResponseTerminosCondiciones aceptarTerminosCondiciones(String usuarioBT) {
		Optional<Empleado> optEmpleado = empleadoDao.findOneByUsuarioBT(usuarioBT.trim());
		if (optEmpleado.isPresent()) {
			Empleado empleado = optEmpleado.get();
			if (!empleado.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "emplado.inactivo", usuarioBT));
			if (empleado.getEstadoMigracion().equals(EstadoMigracion.EXPORTADO.valor)
					|| empleado.getEstadoMigracion().equals(EstadoMigracion.EXPORTADO.valor))
				empleado.setEstadoMigracion(EstadoMigracion.MODIFICADO.valor);
			empleado.setAceptaTerminosCondiciones(true);
			empleado.setFechaAceptacionTc(LocalDateTime.now());
			empleado.setFechaModifica(LocalDateTime.now());
			empleado.setUsuarioModifica(usuarioBT);
			empleado = empleadoDao.save(empleado);
			ResponseTerminosCondiciones response = new ResponseTerminosCondiciones();
			response.setAcepta(empleado.isAceptaTerminosCondiciones());
			response.setFechaAceptacion(empleado.getFechaAceptacionTc());
			return response;
		} else {
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", usuarioBT));
		}

	}

	@Override
	public ResponseTerminosCondiciones consultarTerminosCondiciones(String usuarioBT) {
		Optional<Empleado> optEmpleado = empleadoDao.findOneByUsuarioBT(usuarioBT.trim());
		if (optEmpleado.isPresent()) {
			Empleado empleado = optEmpleado.get();
			if (!empleado.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "emplado.inactivo", usuarioBT));
			ResponseTerminosCondiciones response = new ResponseTerminosCondiciones();
			response.setAcepta(empleado.isAceptaTerminosCondiciones());
			response.setFechaAceptacion(empleado.getFechaAceptacionTc());
			return response;
		} else {
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", usuarioBT));
		}
	}


	@Override
	public Empleado getEmpleadoCorredorTerritorioBt(String usuarioBT) {
		// TODO Auto-generated method stub
		Empleado empleado = buscarPorUsuarioBT(usuarioBT);		
		List<Territorio> territorios = new ArrayList<>();
		
		List<Corredor> corredores = corredorDao.listCorredor(empleado.getId());
		List<UnidadOperativa> unidadesOperativas = unidadOperativaDao.listUnidadOperativa(empleado.getId());
		
		corredores.stream().forEach(e -> territorios.add(e.getTerritorio()));
		
		empleado.setCorredores(corredores);
		empleado.setUnidadesOperativa(unidadesOperativas);
		empleado.setTerritorios(territorios);		
		
		return empleado;
	}


	@Override
	public List<ResponseAcceso> consultaAccesos(String usuarioBT) {
		LOGGER.info("[BEGIN] consultaAccesos {} ", usuarioBT);
		try {
			List<ResponseAcceso> accesos = new ArrayList<>();
			Empleado empleado = buscarPorUsuarioBT(usuarioBT);
			if(empleado.getId() == null)
				throw new ModelNotFoundException("No existe el usuario " + usuarioBT);
			int cantidadSubordinadosNivel1 = empleadoDao.obtenerCantidadSuborninadosNivel1(empleado.getId());
			int cantidadSubordinadosNivel2 = empleadoDao.obtenerCantidadSuborninadosNivel2(empleado.getId());
			List<EmpleadoAcceso> accesosConsolidados = empleadoAccesoDao.findByEmpleadoUsuariobt(empleado.getUsuarioBT());
			accesosConsolidados = accesosConsolidados == null ? new ArrayList<>() : accesosConsolidados;
			accesos = accesosConsolidados.stream().map(a -> {
				ResponseAcceso acceso = new ResponseAcceso();
				acceso.setFuncionalidadCodigo(a.getFuncionalidadCodigo());
				acceso.setFuncionalidadDescripcion(a.getFuncionalidadDescripcion());
				if(a.getFuncionalidadCodigo().equals(FuncionalidadApp.VACACIONES_PROGRAMACION.codigo) && ((cantidadSubordinadosNivel1 + cantidadSubordinadosNivel2 ) > 0) ) {
					acceso.setAprobar(true);
				} else {
					acceso.setAprobar(a.isFuncionalidadAccesoAprobar());
				}
				acceso.setConsultar(a.isFuncionalidadAccesoConsultar());
				acceso.setEliminar(a.isFuncionalidadAccesoEliminar());
				acceso.setModificar(a.isFuncionalidadAccesoModificar());
				acceso.setRegistrar(a.isFuncionalidadAccesoRegistrar());
				acceso.setPerfilCodigo(a.getPerfilAppCodigo());
				acceso.setPerfilDescripcion(a.getPerfilAppDescripcion());
				return acceso;
			}).collect(Collectors.toList());
			return accesos;
		} catch (ModelNotFoundException e) {
			LOGGER.error("[ERROR] consultaAccesos", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			LOGGER.error("[ERROR] consultaAccesos", e);
			throw new AppException(e.getMessage(), e); 
		} catch (Exception e) {
			LOGGER.error("[ERROR] consultaAccesos", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}


	@Override
	public Optional<Empleado> buscarPorId(long id) {
		return empleadoDao.findById(id);
	}


	@Override
	public long obtenerCantidadEmpleadosRedOperaciones() {
		return empleadoDao.contarEmpleadosRedOperaciones();
	}

}
