package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ReporteAccesoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteAcceso;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;

@Service
public class ReporteAccesoServiceImpl implements ReporteAccesoService {
	
	@Autowired
	private ReporteAccesoDao reporteAccesoDao;

	@Override
	public ReporteAcceso registrar(ReporteAcceso acceso, String usuarioOperacion) {
		List<ReporteAcceso> accesos = reporteAccesoDao.buscarPorPuestoYReporte(acceso.getPuesto().getId(), acceso.getTipo().getId());
		if(acceso != null) {
			reporteAccesoDao.deleteAll(accesos);
		}
		acceso.setUsuarioCrea(usuarioOperacion);
		acceso.setFechaCrea(LocalDateTime.now());
		acceso.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		acceso.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		return reporteAccesoDao.save(acceso);
	}

	@Override
	public ReporteAcceso buscarPorId(long id) {
		Optional<ReporteAcceso> optReporteAcceso = reporteAccesoDao.findById(id);
		if(optReporteAcceso.isPresent())
			return optReporteAcceso.get();
		return null;
	}

	@Override
	public ReporteAcceso actualizar(long id, LocalDate fechaEnvio, String usuarioOperacion) {
		ReporteAcceso acceso = buscarPorId(id);
		if(acceso == null)
			return null;
		acceso.setFechaEnvio(fechaEnvio);
		acceso.setFechaModifica(LocalDateTime.now());
		acceso.setUsuarioModifica(usuarioOperacion);
		return reporteAccesoDao.save(acceso);
	}

	@Override
	public void eliminar(long id) {
		reporteAccesoDao.deleteById(id);
	}

}
