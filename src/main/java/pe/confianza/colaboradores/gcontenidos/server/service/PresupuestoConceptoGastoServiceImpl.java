package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PresupuestoAgenciaGastoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PresupuestoConceptoDistribucionGastoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PresupuestoConceptoGastoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PresupuestoPeriodoGastoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoAgenciaGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoConceptoDistribucionGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoConceptoGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoPeriodoGasto;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;

@Service
public class PresupuestoConceptoGastoServiceImpl implements PresupuestoConceptoGastoService {

	@Autowired
	private PresupuestoConceptoGastoDao dao;
	
	@Autowired
	private PresupuestoAgenciaGastoDao presupuestoAgenciaGastoDao;
	
	@Autowired
	private PresupuestoConceptoDistribucionGastoDao presupuestoConceptoDistribucionGastoDao;
	
	@Autowired
	private PresupuestoPeriodoGastoDao presupuestoPeriodoGastoDao;
	
	@Override
	public PresupuestoConceptoGasto buscarPorCodigo(long codigo) {
		Optional<PresupuestoConceptoGasto> opt = dao.findOneByCodigo(codigo);
		if(!opt.isPresent())
			return null;
		PresupuestoConceptoGasto concepto = opt.get();
		if(!concepto.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor))
			return null;
		return concepto;
	}

	@Override
	public PresupuestoConceptoGasto registrarSinDistribucion(PresupuestoConceptoGasto concepto,
			String usuarioOperacion) {
		ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneId.of(Constantes.TIME_ZONE));
		PresupuestoConceptoDistribucionGasto distribucion = new PresupuestoConceptoDistribucionGasto();
		distribucion.setCodigo(zdt.toInstant().toEpochMilli());
		distribucion.setConfigurado(false);
		distribucion.setUsuarioCrea(usuarioOperacion);
		distribucion.setFechaCrea(LocalDateTime.now());
		distribucion.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		distribucion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		distribucion = presupuestoConceptoDistribucionGastoDao.save(distribucion);
		
		concepto.setCodigo(zdt.toInstant().toEpochMilli());
		concepto.setDistribucion(distribucion);
		concepto.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		concepto.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		concepto.setFechaCrea(LocalDateTime.now());
		concepto.setUsuarioCrea(usuarioOperacion);
		return dao.save(concepto);
	}
	
	@Override
	public PresupuestoConceptoGasto registrarDistribucion(PresupuestoConceptoGasto concepto, String usuarioOperacion) {
		PresupuestoConceptoDistribucionGasto distribucion = concepto.getDistribucion();
		distribucion.setUsuarioModifica(usuarioOperacion);
		distribucion.setFechaModifica(LocalDateTime.now());
		distribucion = presupuestoConceptoDistribucionGastoDao.save(distribucion);
		
		concepto.setDistribucion(distribucion);
		concepto.setFechaModifica(LocalDateTime.now());
		concepto.setUsuarioModifica(usuarioOperacion);
		
		
		for (PresupuestoAgenciaGasto conceptoAgencia : concepto.getPresupuestosAgencia()) {
			conceptoAgencia.setFechaCrea(LocalDateTime.now());
			conceptoAgencia.setUsuarioCrea(usuarioOperacion);
			conceptoAgencia.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
			conceptoAgencia.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
			conceptoAgencia.setPresupuestoConcepto(concepto);
			
			List<PresupuestoPeriodoGasto> periodos = conceptoAgencia.getPresupuestoPeriodo();
			for (PresupuestoPeriodoGasto periodo : periodos) {
				periodo.setUsuarioCrea(usuarioOperacion);
				periodo.setFechaCrea(LocalDateTime.now());
				periodo.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
				periodo.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
				periodo.setPresupuestoAgencia(conceptoAgencia);
				periodo.setPresupuestoAgencia(conceptoAgencia);
			}
		}
		concepto = dao.save(concepto);
		return concepto;
	}

	@Override
	public PresupuestoConceptoGasto actualizar(PresupuestoConceptoGasto concepto,
			String usuarioOperacion) {		
		concepto.setFechaModifica(LocalDateTime.now());
		concepto.setUsuarioModifica(usuarioOperacion);
		concepto = dao.save(concepto);
		return concepto;
	}

	

	

}
