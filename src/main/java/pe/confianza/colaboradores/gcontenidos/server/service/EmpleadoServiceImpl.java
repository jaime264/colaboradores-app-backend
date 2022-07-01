package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmpleadoRes;
import pe.confianza.colaboradores.gcontenidos.server.api.spring.EmpleadoApi;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseTerminosCondiciones;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.EmpleadoMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.AgenciaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.CorredorDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PuestoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.TerritorioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.UnidadOperativaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Corredor;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Puesto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Territorio;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadOperativa;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
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
				return em;
			}

		} else {
			return optEmpleado.get();
		}
	}

	@Override
	public List<Empleado> findfechaNacimientoDeHoy() {
		List<Empleado> listEmp = new ArrayList<>();

		Instant instant = Instant.now();
		ZonedDateTime limaQuitoTime = instant.atZone(ZoneId.of("-05:00"));

		int mes = limaQuitoTime.getMonthValue();
		int dia = limaQuitoTime.getDayOfMonth();

		try {
			listEmp = empleadoDao.findfechaNacimientoDeHoy(mes, dia);
		} catch (Exception e) {
			e.getMessage();
		}

		return listEmp;
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

}
