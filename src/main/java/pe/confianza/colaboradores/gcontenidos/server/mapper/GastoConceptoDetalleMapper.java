package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoConceptoDetalle;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoDetalle;

public class GastoConceptoDetalleMapper {
	
	public static ResponseGastoConceptoDetalle convert(GastoConceptoDetalle source) {
		ResponseGastoConceptoDetalle destination = new ResponseGastoConceptoDetalle();
		destination.setCodigo(source.getCodigo());
		destination.setDescripcion(source.getDescripcion());
		destination.setConcepto(GastoConceptoMapper.convert(source.getConcepto()));
		destination.setConceptoPresupuestal(source.getConceptoPresupuestal());
		return destination;
	}

}
