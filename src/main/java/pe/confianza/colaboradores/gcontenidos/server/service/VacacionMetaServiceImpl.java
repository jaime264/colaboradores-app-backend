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
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class VacacionMetaServiceImpl implements VacacionMetaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VacacionMetaServiceImpl.class);

	@Autowired
	private VacacionMetaDao vacacionMetaDao;

	@Autowired
	private PeriodoVacacionService periodoVacacionService;

	@Autowired
	private CargaParametros cargaParametros;

	@Override
	public VacacionMeta obtenerVacacionPorAnio(int anio, long idEmpleado) {
		LOGGER.info("[BEGIN] obtenerVacacionPorAnio");
		List<VacacionMeta> metas = vacacionMetaDao.findByAnioAndIdEmpleado(anio, idEmpleado);
		metas = metas == null ? new ArrayList<>() : metas;
		if (metas.isEmpty())
			return null;
		LOGGER.info("[END] obtenerVacacionPorAnio");
		return metas.get(0);
	}

	@Override
	public VacacionMeta consolidarMetaAnual(Empleado empleado, int anio, String usuarioOperacion) {
		LOGGER.info("[BEGIN] consolidarMetaAnual {} - {}", new Object[] { empleado.getUsuarioBT(), anio });
		LocalDate fechaCorte = cargaParametros.getFechaCorteMeta();
		LocalDate fechaIngreso = empleado.getFechaIngreso();
		VacacionMeta meta = obtenerVacacionPorAnio(anio, empleado.getId());
		if (meta != null) {
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
		if (limitePeriodoVencido != null) {
			LOGGER.info("Limite periodo vencido {}", Arrays.toString(limitePeriodoVencido));
			Optional<PeriodoVacacion> optVencido = periodos.stream()
					.filter(p -> p.getAnio() == limitePeriodoVencido[0].getYear()).findFirst();
			if (optVencido.isPresent()) {
				PeriodoVacacion periodo = optVencido.get();
				meta.setPeriodoVencido(periodo);
				double diasVencidos = periodo.getDerecho() - periodo.getDiasGozados()
						- periodo.getDiasRegistradosGozar() - periodo.getDiasGeneradosGozar()
						- periodo.getDiasAprobadosGozar();
				if (fechaIngreso.getYear() == fechaCorte.getYear() - 1
						&& fechaIngreso.getMonthValue() > fechaCorte.getMonthValue()) {
					diasVencidos = 30 - periodo.getDiasGozados() - periodo.getDiasRegistradosGozar()
							- periodo.getDiasGeneradosGozar() - periodo.getDiasAprobadosGozar();
				}
				meta.setDiasVencidos(diasVencidos);
			}
		}
		LocalDate[] limitePeriodoTrunco = Utilitario.rangoPeriodoTrunco(empleado.getFechaIngreso());
		if (limitePeriodoTrunco != null) {
			LOGGER.info("Limite periodo trunco {}", Arrays.toString(limitePeriodoTrunco));
			Optional<PeriodoVacacion> optTrunco = periodos.stream()
					.filter(p -> p.getAnio() == limitePeriodoTrunco[0].getYear()).findFirst();
			if (optTrunco.isPresent()) {
				PeriodoVacacion periodo = optTrunco.get();
				double derechoPeriodo = Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaCorte);
				if (limitePeriodoVencido == null && fechaIngreso.getMonthValue() >= fechaCorte.getMonthValue()) {
					derechoPeriodo = 30; // Empleado ingreso después del corte del año pasado
				}
				meta.setPeriodoTrunco(periodo);
				meta.setDiasTruncos(derechoPeriodo - periodo.getDiasGozados() - periodo.getDiasRegistradosGozar()
						- periodo.getDiasGeneradosGozar() - periodo.getDiasAprobadosGozar());
			}
		}
		meta.setMetaInicial(Utilitario.calcularMetaVacaciones(empleado.getFechaIngreso(),
				limitePeriodoVencido == null ? 0 : meta.getDiasVencidos()));
		meta.setMeta(meta.getMetaInicial());
		meta.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		meta.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		meta = vacacionMetaDao.saveAndFlush(meta);
		LOGGER.info("[END] consolidarMetaAnual");
		return meta;
	}

	@Override
	public VacacionMeta actualizarMeta(int anio, VacacionProgramacion programacion, boolean cancelarProgramcion,
			String usuarioOperacion) {
		LOGGER.info("[BEGIN] actualizarMeta {} - {}",
				new Object[] { programacion.getPeriodo().getEmpleado().getUsuarioBT(), anio });
		VacacionMeta meta = obtenerVacacionPorAnio(anio, programacion.getPeriodo().getEmpleado().getId());
		if (cancelarProgramcion) {
			meta.setMeta(meta.getMeta() + programacion.getNumeroDias());
		} else {
			meta.setMeta(meta.getMeta() - programacion.getNumeroDias());
		}
		if(meta.getPeriodoVencido() != null) {
			if (meta.getPeriodoVencido().getId() == programacion.getPeriodo().getId()) {
				if (cancelarProgramcion) {
					meta.setDiasVencidos(meta.getDiasVencidos() + programacion.getNumeroDias());
				} else {
					meta.setDiasVencidos(meta.getDiasVencidos() - programacion.getNumeroDias());
				}
			}
		}
		if(meta.getPeriodoTrunco() != null) {
			if (meta.getPeriodoTrunco().getId() == programacion.getPeriodo().getId()) {
				if (cancelarProgramcion) {
					meta.setDiasTruncos(meta.getDiasTruncos() + programacion.getNumeroDias());
				} else {
					meta.setDiasTruncos(meta.getDiasTruncos() - programacion.getNumeroDias());
				}
			}
		}
		
		meta = vacacionMetaDao.saveAndFlush(meta);
		LOGGER.info("[END] actualizarMeta");
		return meta;
	}

	@Override
	public Optional<VacacionMeta> obtenerMeta(long id) {
		return vacacionMetaDao.findById(id);
	}

	@Override
	public VacacionMeta actualizarMeta(VacacionMeta meta, String usuarioOperacion) {
		meta.setFechaModifica(LocalDateTime.now());
		meta.setUsuarioModifica(usuarioOperacion);
		return vacacionMetaDao.save(meta);
	}

}
