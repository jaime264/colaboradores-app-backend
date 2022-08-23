package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoConceptoGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoConceptoGasto;

public class PresupuestoConceptoGastoMapper {
	
	public static ResponsePresupuestoConceptoGasto convert(final PresupuestoConceptoGasto source, final String usuarioGlgAsignado) {
		ResponsePresupuestoConceptoGasto destination = new ResponsePresupuestoConceptoGasto();
		destination.setCodigo(source.getCodigo());
		destination.setConceptoDetalle(GastoConceptoDetalleMapper.convert(source.getConceptoDetalle()));
		destination.setDistribuido(source.isDistribuido());
		destination.setGlgAsignado(GastoGlgAsignadoMapper.convert(source.getGlgAsignado()));
		destination.setPresupuestoAsignado(source.getPresupuestoAsignado());
		destination.setPresupuestoConsumido(source.getPresupuestoConsumido());
		destination.setSolicitudes(0);
		destination.setPuedeAdministrar(usuarioGlgAsignado.equals(source.getGlgAsignado().getEmpleado().getUsuarioBT()) ? true : false);
		return destination;
	}

}
