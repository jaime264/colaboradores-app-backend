package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.GastoPresupuestoDistribucionConceptoAgenciaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.GastoPresupuestoDistribucionConceptoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConceptoAgencia;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;

@Service
public class GastoPresupuestoDistribucionConceptoServiceImpl implements GastoPresupuestoDistribucionConceptoService {

	@Autowired
	private GastoPresupuestoDistribucionConceptoDao dao;
	
	@Autowired
	private GastoPresupuestoDistribucionConceptoAgenciaDao gastoPresupuestoDistribucionConceptoAgenciaDao;
	
	@Override
	public GastoPresupuestoDistribucionConcepto buscarPorCodigo(long codigo) {
		Optional<GastoPresupuestoDistribucionConcepto> opt = dao.findOneByCodigo(codigo);
		if(!opt.isPresent())
			return null;
		GastoPresupuestoDistribucionConcepto concepto = opt.get();
		if(!concepto.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor))
			return null;
		return concepto;
	}

	@Override
	public GastoPresupuestoDistribucionConcepto registrarSinDistribucion(GastoPresupuestoDistribucionConcepto concepto,
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
	public GastoPresupuestoDistribucionConcepto actualizarDistribuido(GastoPresupuestoDistribucionConcepto concepto,
			String usuarioOperacion) {
		concepto.setDistribuido(false);
		concepto.setFechaModifica(LocalDateTime.now());
		concepto.setUsuarioModifica(usuarioOperacion);
		if(!concepto.getDistribucionesAgencia().isEmpty())
			concepto.setDistribuido(true);
		concepto = dao.save(concepto);
		for (GastoPresupuestoDistribucionConceptoAgencia conceptoAgencia : concepto.getDistribucionesAgencia()) {
			if(conceptoAgencia.getId() == null) {
				conceptoAgencia.setFechaCrea(LocalDateTime.now());
				conceptoAgencia.setUsuarioCrea(usuarioOperacion);
				conceptoAgencia.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
				conceptoAgencia.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
				conceptoAgencia.setDistribucionConcepto(concepto);
			} else {
				conceptoAgencia.setFechaModifica(LocalDateTime.now());
				conceptoAgencia.setUsuarioModifica(usuarioOperacion);
				conceptoAgencia.setDistribucionConcepto(concepto);
			}
			gastoPresupuestoDistribucionConceptoAgenciaDao.save(conceptoAgencia);
		}
		return concepto;
	}

	

}
