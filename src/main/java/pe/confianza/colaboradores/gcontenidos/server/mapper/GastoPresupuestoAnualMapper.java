package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoPresupuestalAnual;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoAnual;

public class GastoPresupuestoAnualMapper {
	
	public static ResponseGastoPresupuestalAnual convert(final GastoPresupuestoAnual source) {
		ResponseGastoPresupuestalAnual destination = new ResponseGastoPresupuestalAnual();
		destination.setActivo(source.isActivo());
		destination.setCodigo(source.getCodigo());
		destination.setDescripcion(source.getDescripcion());
		destination.setPresupuestoUsado(0);
		return destination;
	}

}
