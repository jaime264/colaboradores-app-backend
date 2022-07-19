package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseEmpleadoMeta;
import pe.confianza.colaboradores.gcontenidos.server.controller.RequestFiltroEmpleadoMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionMetaResumenDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMetaResumen;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;

@Service
public class VacacionMetaResumenServiceImpl implements VacacionMetaResumenService {
	
	@Autowired
	private VacacionMetaResumenDao vacacionMetaResumenDao;
	
	@Autowired
	private CargaParametros cargaParametros;

	@Override
	public List<VacacionMetaResumen> listarResumenAnio(int anio) {
		List<VacacionMetaResumen> resumenes = vacacionMetaResumenDao.listarResumenPorAnio(anio);
		resumenes = resumenes == null ? new ArrayList<>() : resumenes;
		return resumenes;
	}

	@Override
	public Page<ResponseEmpleadoMeta> listarPorPuesto(RequestFiltroEmpleadoMeta filtro) {
		int anio = cargaParametros.getMetaVacacionAnio();
		Pageable paginacion = PageRequest.of(filtro.getNumeroPagina(), filtro.getTamanioPagina());
		Page<VacacionMetaResumen> metas = vacacionMetaResumenDao.consultarPorPuestoId(filtro.getIdPuesto(), anio, paginacion);
		return metas.map(m -> {
			ResponseEmpleadoMeta response = new ResponseEmpleadoMeta();
			response.setId(m.getMetaId());
			response.setEmpleado(m.getEmpleadoNombreCompleto());
			response.setFechaIngreso(m.getEmpleadoFechaIngreso());
			response.setMeta(m.getMetaInicial());
			response.setDiasProgramados(m.getMetaInicial() - m.getMeta());
			return response;
		});
	}

	@Override
	public Page<ResponseEmpleadoMeta> listarPorNombreEmpleado(RequestFiltroEmpleadoMeta filtro) {
		int anio = cargaParametros.getMetaVacacionAnio();
		Pageable paginacion = PageRequest.of(filtro.getNumeroPagina(), filtro.getTamanioPagina());
		Page<VacacionMetaResumen> metas = vacacionMetaResumenDao.consultarPorNombre(filtro.getNombre().toUpperCase(), anio, paginacion);
		return metas.map(m -> {
			ResponseEmpleadoMeta response = new ResponseEmpleadoMeta();
			response.setId(m.getMetaId());
			response.setEmpleado(m.getEmpleadoNombreCompleto());
			response.setFechaIngreso(m.getEmpleadoFechaIngreso());
			response.setPuesto(m.getPuestoDescripcion());
			response.setMeta(m.getMetaInicial());
			response.setDiasProgramados(m.getMetaInicial() - m.getMeta());
			return response;
		});
	}

}
