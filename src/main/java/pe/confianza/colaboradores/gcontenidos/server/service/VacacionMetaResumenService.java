package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseEmpleadoMeta;
import pe.confianza.colaboradores.gcontenidos.server.controller.RequestFiltroEmpleadoMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMetaResumen;

public interface VacacionMetaResumenService {

	List<VacacionMetaResumen> listarResumenAnio(int anio);
	
	Page<ResponseEmpleadoMeta> listarPorPuesto(RequestFiltroEmpleadoMeta filtro);
	
	Page<ResponseEmpleadoMeta> listarPorNombreEmpleado(RequestFiltroEmpleadoMeta filtro);
}
