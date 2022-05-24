package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmpleadoRes;
import pe.confianza.colaboradores.gcontenidos.server.api.spring.EmpleadoApi;
import pe.confianza.colaboradores.gcontenidos.server.mapper.EmpleadoMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.AgenciaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PuestoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Puesto;

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

	@Override
	public Empleado actualizarInformacionEmpleado(String usuarioBT) {
		LOGGER.info("[BEGIN] actualizarInformacionEmpleado");
		EmpleadoRes empleadoSpring = empleadoApi.getPerfil(usuarioBT);
		LOGGER.info("EMpleado: " + empleadoSpring.toString());
		Optional<Puesto> optPuesto = puestoDao.findOneByCodigo(empleadoSpring.getIdCargo());
		Optional<Agencia> optAgencia = agenciaDao.findOneByCodigo(empleadoSpring.getIdSucursal().trim());
		Optional<Empleado> optEmpleado = empleadoDao.findOneByUsuarioBT(usuarioBT);
		Empleado empleado = optEmpleado.isPresent() ? optEmpleado.get() : EmpleadoMapper.convert(empleadoSpring);
		empleado.setPuesto(optPuesto.isPresent() ? optPuesto.get() : null);
		empleado.setAgencia(optAgencia.isPresent() ? optAgencia.get() : null);
		empleado.setFechaActualizacion(LocalDateTime.now());
		empleado = empleadoDao.save(empleado);
		LOGGER.info("[END] actualizarInformacionEmpleado");
		return empleado;
	}

	public List<Empleado> listEmpleado() {

		empleadoDao.findAll();

		return null;
	}

	public Empleado listEmpleadoById(Long id) {

		return empleadoDao.findById(id).get();

	}

}
