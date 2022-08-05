package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteTipo;

public interface ReporteTipoService {
	
	List<ReporteTipo> listarActivos();
	
	ReporteTipo buscarPorCodigo(String codigo);

}
