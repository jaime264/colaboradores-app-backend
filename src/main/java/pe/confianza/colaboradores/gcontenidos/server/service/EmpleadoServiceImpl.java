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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmplVacPerRes;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmpleadoRes;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionPeriodo;
import pe.confianza.colaboradores.gcontenidos.server.api.spring.EmpleadoApi;
import pe.confianza.colaboradores.gcontenidos.server.mapper.EmpleadoMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.AgenciaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PuestoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Puesto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;

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
		vacacionMetaService.consolidarMetaAnual(empleado, LocalDate.now().getYear() + 1 , usuarioBT);
		LOGGER.info("[END] actualizarInformacionEmpleado");
		return empleado;
	}

	@Override
	public List<EmplVacPerRes> listEmpleadoByprogramacion(Long codigo) {

		List<EmplVacPerRes> listEmp = new ArrayList<EmplVacPerRes>();
		List<VacacionPeriodo> listVp = new ArrayList<VacacionPeriodo>();

		List<Empleado> emsByJefe = empleadoDao.findByCodigoJefe(codigo);

		if (!CollectionUtils.isEmpty(emsByJefe)) {

			for (Empleado e : emsByJefe) {
				EmplVacPerRes emp = new EmplVacPerRes();
				emp.setNombres(e.getNombres());
				emp.setApellidoPaterno(e.getApellidoPaterno());
				emp.setApellidoMaterno(e.getApellidoMaterno());
				emp.setIdEmpleado(e.getId());
				emp.setPuesto(e.getPuesto().getDescripcion());
				emp.setUsuarioBt(e.getUsuarioBT());

				List<VacacionProgramacion> lVp = empleadoDao.findPeriodosByEmpleado(e.getId());

				for (VacacionProgramacion v : lVp) {
					VacacionPeriodo vp = new VacacionPeriodo();
					vp.setIdProgramacion(v.getId());
					vp.setFechaInicio(v.getFechaInicio());
					vp.setFechaFin(v.getFechaFin());
					vp.setIdEstado(vp.getIdEstado());
					vp.setPeriodo(v.getPeriodo().getDescripcion());

					listVp.add(vp);
				}
				if (!lVp.isEmpty()) {
					emp.setVacacionPeriodo(listVp);
				}

				listEmp.add(emp);
			}

		}

		return listEmp;
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
	public int obtenerCantidadEmpleadosPorUnidadNegocio(long codigoUnidadNegocio) {
		return empleadoDao.obtenerCantidadEmpleadosPorUnidadNegocio(codigoUnidadNegocio);
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

}
