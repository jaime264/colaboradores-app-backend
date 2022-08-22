package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PresupuestoAgenciaGastoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PresupuestoConceptoGastoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoAgenciaGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoConceptoGasto;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;

@Service
public class PresupuestoConceptoGastoServiceImpl implements PresupuestoConceptoGastoService {

	@Autowired
	private PresupuestoConceptoGastoDao dao;
	
	@Autowired
	private PresupuestoAgenciaGastoDao presupuestoAgenciaGastoDao;
	
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
		concepto.setCodigo(zdt.toInstant().toEpochMilli());
		concepto.setDistribuido(false);
		concepto.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		concepto.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		concepto.setFechaCrea(LocalDateTime.now());
		concepto.setUsuarioCrea(usuarioOperacion);
		return dao.save(concepto);
	}

	@Override
	public PresupuestoConceptoGasto actualizarDistribuido(PresupuestoConceptoGasto concepto,
			String usuarioOperacion) {
		concepto.setDistribuido(false);
		concepto.setFechaModifica(LocalDateTime.now());
		concepto.setUsuarioModifica(usuarioOperacion);
		if(!concepto.getPresupuestosAgencia().isEmpty())
			concepto.setDistribuido(true);
		concepto = dao.save(concepto);
		for (PresupuestoAgenciaGasto conceptoAgencia : concepto.getPresupuestosAgencia()) {
			if(conceptoAgencia.getId() == null) {
				conceptoAgencia.setFechaCrea(LocalDateTime.now());
				conceptoAgencia.setUsuarioCrea(usuarioOperacion);
				conceptoAgencia.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
				conceptoAgencia.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
			} else {
				conceptoAgencia.setFechaModifica(LocalDateTime.now());
				conceptoAgencia.setUsuarioModifica(usuarioOperacion);
			}
			conceptoAgencia.setPresupuestoConcepto(concepto);
			presupuestoAgenciaGastoDao.save(conceptoAgencia);
		}
		return concepto;
	}

	

}
