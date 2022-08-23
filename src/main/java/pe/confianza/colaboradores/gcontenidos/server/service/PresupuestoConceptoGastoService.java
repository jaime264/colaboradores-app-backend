package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoConceptoGasto;

public interface PresupuestoConceptoGastoService {
	
	PresupuestoConceptoGasto registrarSinDistribucion(PresupuestoConceptoGasto concepto, String usuarioOperacion);
	
	PresupuestoConceptoGasto actualizar(PresupuestoConceptoGasto concepto, String usuarioOperacion);
	
	PresupuestoConceptoGasto buscarPorCodigo(long codigo);

}
