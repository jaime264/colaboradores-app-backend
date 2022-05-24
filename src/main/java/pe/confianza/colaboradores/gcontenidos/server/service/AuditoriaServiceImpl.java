package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao.AuditoriaDao;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Auditoria;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {
	
	@Autowired 
	private AuditoriaDao auditoriaDao;
	
	private static Logger logger = LoggerFactory.getLogger(AuditoriaServiceImpl.class);

	@Override
	public void createAuditoria(String idAplicacion, String idProceso, Integer status, String msgStatus, BsonDocument data) {
		List<Auditoria> auditoriaLog = new ArrayList<Auditoria>();
		List<Auditoria> auditoriaOut = new ArrayList<Auditoria>();
		try {
			Auditoria log = new Auditoria();
			log.setAplicacion(idAplicacion);
			log.setProceso(idProceso);
			log.setStatus(status);
			log.setMsgStatus(msgStatus);
			log.setDatos(data);
			log.setRegistro(new Date());
			auditoriaLog.add(log);
			auditoriaOut = auditoriaDao.saveAll(auditoriaLog);
			if (auditoriaOut.size() > 0) {
				logger.info("Log creado correctamente " + auditoriaOut.get(0).get_id());
			} else {
				logger.info("Ocurrio un error al guardar el log");
			}
		} catch (Exception ex) {
			logger.error("Registro creado correctamente", ex.getMessage());
		}
	}

}
