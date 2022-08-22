package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConcepto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoDetalle;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastosSolicitud;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoPeriodoGasto;

public interface SolicitudGastoService {

	public List<GastoConceptoTipo> listarTipoGastoByEmpleado();
	
	public List<GastoConcepto> listarConceptoByTipoGasto(Long idTipoGasto);
	
	public GastosSolicitud registrarGastoSolicitud(GastosSolicitud gasto);
	
	public GastoConceptoTipo obtenerTipoGastoById(Long id);
	
	public GastoConcepto obtenerConceptoById(Long id);
	
	GastoConceptoDetalle obtenerPorId(long id);
	
	PresupuestoPeriodoGasto obtenerPeriodoActual(long idAgencia, long idConceptodetalle);
}
