package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoAnualDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConcepto;

public class GastoPresupuestoDistribucionConceptoMapper {
	
	public static ResponsePresupuestoAnualDistribucionConcepto convert(final GastoPresupuestoDistribucionConcepto source) {
		ResponsePresupuestoAnualDistribucionConcepto destination = new ResponsePresupuestoAnualDistribucionConcepto();
		destination.setCodigo(source.getCodigo());
		destination.setCuentaContable(source.getCuentaContable());
		destination.setGlgAsignado(GastoGlgAsignadoMapper.convert(source.getGlgAsignado()));
		destination.setPresupuesto(source.getPresupuesto());
		destination.setDistribuido(source.isDistribuido());
		return destination;
	}

}
