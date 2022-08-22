package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoGeneralGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoGeneralGasto;

public class PresupuestoGeneralGastoMapper {
	
	public static ResponsePresupuestoGeneralGasto convert(final PresupuestoGeneralGasto source) {
		ResponsePresupuestoGeneralGasto destination = new ResponsePresupuestoGeneralGasto();
		destination.setActivo(source.isActivo());
		destination.setCodigo(source.getCodigo());
		destination.setDescripcion(source.getDescripcion());
		destination.setPresupuestoAsignado(source.getPresupuestoAsignado());
		destination.setPresupuestoConsumido(source.getPresupuestoAsignado());
		destination.setSolicitudes(0);
		return destination;
	}

}
