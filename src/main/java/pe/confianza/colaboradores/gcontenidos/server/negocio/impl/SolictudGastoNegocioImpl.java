package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.BsonDocument;
import org.bson.codecs.jsr310.LocalDateCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCentroCostos;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGastoEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestTipoGasto;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConcepto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoDetalle;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConceptoAgenciaPeriodo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastosSolicitud;
import pe.confianza.colaboradores.gcontenidos.server.negocio.SolictudGastoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.AgenciaService;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;
import pe.confianza.colaboradores.gcontenidos.server.service.SolicitudGastoService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;

@Service
public class SolictudGastoNegocioImpl implements SolictudGastoNegocio {

	private static Logger logger = LoggerFactory.getLogger(ParametrosServiceImpl.class);

	@Autowired
	private AuditoriaService auditoriaService;

	@Autowired
	SolicitudGastoService solicitudGastoService;
	
	@Autowired
	EmpleadoService empleadoService;
	
	@Autowired
	AgenciaService agenciaService;

	@Override
	public void registrarAuditoria(int status, String mensaje, Object data) {
		Gson gson = new Gson();
		try {
			String jsonData = gson.toJson(data);
			auditoriaService.createAuditoria("002", Constantes.GASTOS_GESTIONAR_PRESUPUESTO, status, mensaje,
					BsonDocument.parse(jsonData));
		} catch (Exception e) {
			logger.error("[ERROR] registrarAuditoria", e);
		}

	}

	@Override
	public List<GastoConceptoTipo> listarTipoGasto(RequestTipoGasto peticion) {
		// TODO Auto-generated method stub

		List<GastoConceptoTipo> ListTipoGasto = solicitudGastoService.listarTipoGastoByEmpleado();
		registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);

		return ListTipoGasto;
	}

	@Override
	public List<GastoConcepto> listarConceptoByTipoGasto(RequestConcepto reqConcepto) {
		// TODO Auto-generated method stub

		List<GastoConcepto> listConceptoByTipo = solicitudGastoService.listarConceptoByTipoGasto(reqConcepto.getIdTipoGasto());
		registrarAuditoria(Constantes.COD_OK, Constantes.OK, reqConcepto);
		return listConceptoByTipo;
	}

	@Override
	public Object listarCentrosCostoByAgencia(RequestCentroCostos peticion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public GastosSolicitud registrarGastoEmpleado(RequestGastoEmpleado gasto) {
		// TODO Auto-generated method stub
		GastosSolicitud gastosSolicitud = new GastosSolicitud();
		Optional<Empleado> empleado = empleadoService.buscarPorId(gasto.getIdEmpleado());
		Agencia agencia = agenciaService.buscarPorId(gasto.getIdAgencia());
		GastoConceptoTipo gastoConceptoTipo = solicitudGastoService.obtenerTipoGastoById(gasto.getIdGastoTipo());
		GastoConceptoDetalle gastoConceptodetalle = solicitudGastoService.obtenerPorId(gasto.getIdConceptoDetalle());
		GastoPresupuestoDistribucionConceptoAgenciaPeriodo periodo = solicitudGastoService.obtenerPeriodoActual(agencia.getId(), gastoConceptodetalle.getId() );
		if(periodo == null)
			throw new AppException("Aún no se encuentra distribuido el presupuesto anual");
		
		gastosSolicitud.setIdEmpleado(empleado.get());
		gastosSolicitud.setIdAgencia(agencia);
		gastosSolicitud.setIdGastoConceptoTipo(gastoConceptoTipo);
		gastosSolicitud.setGastoConceptoDetalle(gastoConceptodetalle);
		gastosSolicitud.setMontoGasto(gasto.getMonto());
		gastosSolicitud.setTerminosCondiciones(gasto.getTerminosCondiciones());
		gastosSolicitud.setMotivo(gasto.getMotivo());
		gastosSolicitud.setPeriodo(periodo);
		
		
		gastosSolicitud.setUsuarioCrea(gasto.getUsuarioBt());
		gastosSolicitud.setFechaCrea(LocalDateTime.now());
		gastosSolicitud.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		gastosSolicitud.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		
		GastosSolicitud resRastosSolicitud = solicitudGastoService.registrarGastoSolicitud(gastosSolicitud);
		registrarAuditoria(Constantes.COD_OK, Constantes.OK, gasto);

		return resRastosSolicitud;
		
	}

}
