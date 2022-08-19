package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConcepto;

public interface GastoPresupuestoDistribucionConceptoService {
	
	GastoPresupuestoDistribucionConcepto registrarSinDistribucion(GastoPresupuestoDistribucionConcepto concepto, String usuarioOperacion);
	
	GastoPresupuestoDistribucionConcepto actualizarDistribuido(GastoPresupuestoDistribucionConcepto concepto, String usuarioOperacion);
	
	GastoPresupuestoDistribucionConcepto buscarPorCodigo(long codigo);

}
