package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.CentroCostoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.GastoConceptoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.GastoConceptoDetalleDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.GastoConceptoTipoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PresupuestoPeriodoGastoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.GastosSolicitudDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.CentroCosto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConcepto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoDetalle;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastosSolicitud;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoPeriodoGasto;

@Service
public class SolicitudGastoServiceImpl implements SolicitudGastoService {

	private static Logger logger = LoggerFactory.getLogger(VacacionProgramacionServiceImpl.class);
	
	@Autowired
	GastoConceptoTipoDao gastoConceptoTipoDao;
	
	@Autowired
	GastoConceptoDao gastoConceptoDao;
	
	@Autowired
	GastosSolicitudDao gastosSolicitudDao;

	@Autowired
	GastoConceptoDetalleDao gastoConceptoDetalleDao;
	
	@Autowired
	PresupuestoPeriodoGastoDao presupuestoPeriodoGastoDao;

	@Autowired
	CentroCostoDao centroCostoDao;
	

	public List<GastosSolicitud> listarGastoSolicitudByEmpleado(Long idEmpleado){
		logger.info("[BEGIN] listarTipoGastoByEmpleado");
		List<GastosSolicitud> gastosSolicitud = gastosSolicitudDao.obtenerTipoGastoByEmpleado(idEmpleado);
		gastosSolicitud = gastosSolicitud == null ? new ArrayList<>() : gastosSolicitud;
		logger.info("[END] listarTipoGastoByEmpleado");
		return gastosSolicitud;
	}
	
	public List<GastoConceptoTipo> listarTipoGasto(){
		logger.info("[BEGIN] listarTipoGastoByEmpleado");
		List<GastoConceptoTipo> gastosConceptoTipo = gastoConceptoTipoDao.findAll();
		gastosConceptoTipo = gastosConceptoTipo == null ? new ArrayList<>() : gastosConceptoTipo;
		logger.info("[END] listarTipoGastoByEmpleado");
		return gastosConceptoTipo;
	}
	
	public List<GastoConcepto> listarConceptoByTipoGasto(Long idTipoGasto){
		logger.info("[BEGIN] listarTipoGastoByEmpleado");
		List<GastoConcepto> listConceptoByTipo = gastoConceptoDao.findAll();
		listConceptoByTipo = listConceptoByTipo == null ? new ArrayList<>() : listConceptoByTipo;
		logger.info("[END] listarTipoGastoByEmpleado");
		return listConceptoByTipo;
	}
	
	public GastosSolicitud registrarGastoSolicitud(GastosSolicitud gasto){
		logger.info("[BEGIN] listarTipoGastoByEmpleado");
		GastosSolicitud gastoSolicitud = gastosSolicitudDao.save(gasto);
		gastoSolicitud = gastoSolicitud == null ? new GastosSolicitud() : gastoSolicitud;
		logger.info("[END] listarTipoGastoByEmpleado");
		return gastoSolicitud;
	}
	
	public GastoConceptoTipo obtenerTipoGastoById(Long id){
		logger.info("[BEGIN] listarTipoGastoByEmpleado");
		Optional<GastoConceptoTipo> gastoConceptoTipo = gastoConceptoTipoDao.findById(id);
		logger.info("[END] listarTipoGastoByEmpleado");
		return gastoConceptoTipo.get();
	}
	
	public GastoConcepto obtenerConceptoById(Long id){
		logger.info("[BEGIN] listarTipoGastoByEmpleado");
		Optional<GastoConcepto> concepto = gastoConceptoDao.findById(id);
		logger.info("[END] listarTipoGastoByEmpleado");
		return concepto.get();
	}

	@Override
	public GastoConceptoDetalle obtenerPorId(long id) {
		Optional<GastoConceptoDetalle> opt = gastoConceptoDetalleDao.findById(id);
		return opt.get();
	}

	@Override
	public PresupuestoPeriodoGasto obtenerPeriodoActual(long idAgencia,
			long idConceptodetalle) {
		List<PresupuestoPeriodoGasto> periodosActivos = presupuestoPeriodoGastoDao.buscarPeriodoActual(idAgencia, idConceptodetalle);
		if(periodosActivos == null)
			return null;
		if(periodosActivos.isEmpty())
			return null;
		if(periodosActivos.size() > 1)
			return null;
		return periodosActivos.get(0);
	}

	@Override
	public List<CentroCosto> obtenerCentroCostosByAgencia(String codAgencia) {
		logger.info("[BEGIN] obtenerCentroCostosByAgencia");
		List<CentroCosto> listCentroCosto = centroCostoDao.obtenerCentroCostoByAgencia(codAgencia);
		logger.info("[BEGIN] obtenerCentroCostosByAgencia");
		return listCentroCosto;
	}
	
}
