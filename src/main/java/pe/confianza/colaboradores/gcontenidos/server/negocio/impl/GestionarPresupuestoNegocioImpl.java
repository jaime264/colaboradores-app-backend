package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@Service
public class GestionarPresupuestoNegocioImpl implements GestionarPresupuestoNegocio {
	
	private static Logger logger = LoggerFactory.getLogger(ParametrosServiceImpl.class);
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public void registrarAuditoria(int status, String mensaje, Object data) {
		Gson gson = new Gson();
		try {
			String jsonData = gson.toJson(data);
			auditoriaService.createAuditoria("002", Constantes.GASTOS_GESTIONAR_PRESUPUESTO,
					status, mensaje, BsonDocument.parse(jsonData));
		} catch (Exception e) {
			logger.error("[ERROR] registrarAuditoria", e);
		}
		
	}

}
