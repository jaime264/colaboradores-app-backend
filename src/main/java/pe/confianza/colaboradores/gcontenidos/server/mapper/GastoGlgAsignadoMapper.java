package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGlgAsignado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoGlgAsignado;

public class GastoGlgAsignadoMapper {
	
	public static ResponseGlgAsignado convert(final GastoGlgAsignado source) {
		ResponseGlgAsignado destination = new ResponseGlgAsignado();
		destination.setCodigo(source.getCodigo());
		destination.setNombreCompleto(source.getEmpleado().getNombreCompleto());
		return destination;
	}

}
