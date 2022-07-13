package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametroUnidad;

public class ParametroMapper {
	
	public static ResponseParametro convert(final Parametro source) {
		ResponseParametro destination = new ResponseParametro();
		destination.setCodigo(source.getCodigo());
		destination.setDescripcion(source.getDescripcion());
		destination.setValor(source.getValor().toString());
		destination.setId(source.getId());
		destination.setUnidad(ParametroUnidad.getDescripcionPorCodigo(source.getUnidad()));
		return destination;
	}
	
	public static Parametro convert(final RequestParametro source) {
		Parametro destination = new Parametro();
		destination.setCodigo(source.getCodigo().toUpperCase().replaceAll(" ", "_"));
		destination.setDescripcion(source.getDescripcion());
		destination.setValor(source.getValor());
		return destination;
	}

}
