package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

public class EmpleadoMapper {
	
	public static Empleado convert(final pe.confianza.colaboradores.gcontenidos.server.bean.Empleado source) {
		Empleado destination = new Empleado();
		destination.setCodigo(source.getIdEmpleado());
		destination.setNombres(source.getNombres() != null ? source.getNombres().trim() : null);
		destination.setApellidoPaterno(source.getApePaterno() != null ? source.getApePaterno().trim() : null);
		destination.setApellidoMaterno(source.getApeMaterno() != null ? source.getApeMaterno().trim() : null);
		destination.setPuesto(null);
		destination.setAgencia(null);
		destination.setFechaNacimiento(Utilitario.obtenerLocalDate(source.getFechaNac()));
		destination.setFechaIngreso(Utilitario.obtenerLocalDate(source.getFechaIng()));
		destination.setEmail(source.getEmail() != null ? source.getEmail().trim() : null);
		destination.setCelular(source.getCelular() != null ? source.getCelular().trim() : null);
		destination.setDireccion(source.getDireccion() != null ? source.getDireccion().trim() : null);
		destination.setSexo(source.getSexo() != null ? source.getSexo().trim() : null);
		destination.setUsuarioBT(source.getUsuarioBT() != null ? source.getUsuarioBT().trim() : null);
		return destination;
	}

}
