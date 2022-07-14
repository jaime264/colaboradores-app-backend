package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionReprogramacionContadorDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionReprogramacionContador;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;

@Service
public class VacacionReprogramacionContadorServiceImpl implements VacacionReprogramacionContadorService {
	
	
	@Autowired
	private VacacionReprogramacionContadorDao dao;

	@Override
	public Optional<VacacionReprogramacionContador> obtenerPorEmpleadoAndAnio(Empleado empleado, int anio) {
		return dao.obtenerPorEmpleadoAndAnio(empleado.getId(), anio);
	}

	@Override
	public VacacionReprogramacionContador registrar(long idEmpleado, int anio, String usuarioOperacion) {
		Empleado empleado = new Empleado();
		empleado.setId(idEmpleado);
		Optional<VacacionReprogramacionContador> opt = obtenerPorEmpleadoAndAnio(empleado, anio);
		if(opt.isPresent())
			return null;
		VacacionReprogramacionContador contador = new VacacionReprogramacionContador();
		contador.setEmpleado(empleado);
		contador.setAnio(anio);
		contador.setReprogramaciones(0);
		contador.setUsuarioCrea(usuarioOperacion);
		contador.setFechaCrea(LocalDateTime.now());
		contador.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		contador.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		return dao.save(contador);
	}

	@Override
	public VacacionReprogramacionContador actualizarContador(long idEmpleado, int anio,  String usuarioOperacion) {
		Empleado empleado = new Empleado();
		empleado.setId(idEmpleado);
		Optional<VacacionReprogramacionContador> opt = obtenerPorEmpleadoAndAnio(empleado, anio);
		if(opt.isPresent())
			return null;
		VacacionReprogramacionContador contador = opt.get();
		contador.setReprogramaciones(contador.getReprogramaciones() + 1);
		contador.setUsuarioModifica(usuarioOperacion);
		contador.setFechaModifica(LocalDateTime.now());
		return dao.save(contador);
	}
	
	

}
