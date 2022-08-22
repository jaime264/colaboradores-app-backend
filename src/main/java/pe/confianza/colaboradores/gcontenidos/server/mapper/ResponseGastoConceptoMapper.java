package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoConcepto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConcepto;

public class ResponseGastoConceptoMapper {
	
	public static ResponseGastoConcepto convert(GastoConcepto source) {
		ResponseGastoConcepto destination = new ResponseGastoConcepto();
		destination.setCodigo(source.getCodigo());
		destination.setDescripcion(source.getDescripcion());
		return destination;
	}

}
