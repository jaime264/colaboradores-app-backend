package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionMetaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametrosConstants;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class VacacionMetaServiceImpl implements VacacionMetaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VacacionMetaServiceImpl.class);
	
	@Autowired
	private VacacionMetaDao vacacionMetaDao;
	
	@Autowired
	private PeriodoVacacionService periodoVacacionService;
	
	@Autowired
	private ParametrosConstants parametrosConstants;
	
	@Override
	public VacacionMeta obtenerVacacionPorAnio(int anio, long idEmpleado) {
		LOGGER.info("[BEGIN] obtenerVacacionPorAnio");
		List<VacacionMeta> metas = vacacionMetaDao.findByAnioAndIdEmpleado(anio, idEmpleado);
		metas = metas == null ? new ArrayList<>() : metas;
		if(metas.isEmpty())
			return null;
		LOGGER.info("[END] obtenerVacacionPorAnio");
		return metas.get(0);
	}

	@Override
	public VacacionMeta consolidarMetaAnual(Empleado empleado, int anio, String usuarioOperacion) {
		LOGGER.info("[BEGIN] consolidarMetaAnual {} - {}" , new Object[] {empleado.getUsuarioBT(), anio});
		LocalDate fechaCorte = parametrosConstants.getFechaCorteMeta(anio - 1);
		LocalDate fechaIngreso = empleado.getFechaIngreso();
		VacacionMeta meta = obtenerVacacionPorAnio(anio, empleado.getId());
		if(meta != null) {
			meta.setFechaModifica(LocalDateTime.now());
			meta.setUsuarioModifica(usuarioOperacion);
		} else {
			meta = new VacacionMeta();
			meta.setUsuarioCrea(usuarioOperacion);
			meta.setFechaCrea(LocalDateTime.now());
		}
		meta.setEmpleado(empleado);
		meta.setAnio(anio);
		meta.setFechaCorte(fechaCorte);
		meta.setDiasTruncos(0);
		meta.setDiasVencidos(0);
		List<PeriodoVacacion> periodos = periodoVacacionService.consultar(empleado);
		LocalDate[] limitePeriodoVencido = Utilitario.rangoPeriodoVencido(empleado.getFechaIngreso());
		if(limitePeriodoVencido != null) {
			LOGGER.info("Limite periodo vencido {}", Arrays.toString(limitePeriodoVencido));
			Optional<PeriodoVacacion> optVencido = periodos.stream().filter(p -> p.getAnio() == limitePeriodoVencido[0].getYear()).findFirst();
			if(optVencido.isPresent()) {
				PeriodoVacacion periodo = optVencido.get();
				meta.setPeriodoVencido(periodo);
				double diasVencidos = periodo.getDerecho() - periodo.getDiasGozados() - periodo.getDiasRegistradosGozar() - periodo.getDiasGeneradosGozar() - periodo.getDiasAprobadosGozar();
				if(fechaIngreso.getYear() == fechaCorte.getYear() - 1 && fechaIngreso.getMonthValue() > fechaCorte.getMonthValue()) {
					diasVencidos = 30 - periodo.getDiasGozados() - periodo.getDiasRegistradosGozar() - periodo.getDiasGeneradosGozar() - periodo.getDiasAprobadosGozar();
				}
				meta.setDiasVencidos(diasVencidos);
			}
		}
		LocalDate[] limitePeriodoTrunco = Utilitario.rangoPeriodoTrunco(empleado.getFechaIngreso());
		if(limitePeriodoTrunco != null) {
			LOGGER.info("Limite periodo trunco {}", Arrays.toString(limitePeriodoTrunco));
			Optional<PeriodoVacacion> optTrunco = periodos.stream().filter(p -> p.getAnio() == limitePeriodoTrunco[0].getYear()).findFirst();
			if(optTrunco.isPresent()) {
				PeriodoVacacion periodo = optTrunco.get();
				meta.setPeriodoTrunco(periodo);
				meta.setDiasTruncos(periodo.getDerecho() - periodo.getDiasGozados() - periodo.getDiasRegistradosGozar() - periodo.getDiasGeneradosGozar() - periodo.getDiasAprobadosGozar());
			}
		}
		meta.setMeta(Utilitario.calcularMetaVacaciones(empleado.getFechaIngreso(), limitePeriodoVencido == null ? 0 : meta.getDiasVencidos()));
		meta = vacacionMetaDao.saveAndFlush(meta);
		LOGGER.info("[END] consolidarMetaAnual");
		return meta;
	}

	

	
	


}
