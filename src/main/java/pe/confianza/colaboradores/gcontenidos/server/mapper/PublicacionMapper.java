package pe.confianza.colaboradores.gcontenidos.server.mapper;

import java.util.stream.Collectors;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestPublicacionGestorContenido;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Video;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;

public class PublicacionMapper {
	
	public static Publicacion convert(RequestPublicacionGestorContenido source) {
		Publicacion destination = new Publicacion();
		destination.setDescripcion(source.getDescripcion());
		destination.setUsuarioBt(source.getUsuarioBt());
		destination.setUsuarioCrea(source.getUsuarioBt());
		destination.setFlagReacion(source.isFlagReacion());
		destination.setFlagAprobacion(1);
		destination.setFlagPermanente(source.isFlagPermanente());
		destination.setGestorContenido(true);
		destination.setMenu(source.getMenu());
		destination.setSubmenu(source.getSubmenu());
		destination.setCategoria(source.getCategoria());
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		if(!source.getImagenes().isEmpty()) {
			destination.setImagenes(source.getImagenes().stream().map(i -> {
				Imagen imagen = new Imagen();
				imagen.setUrl(i.getUrl());
				imagen.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
				return imagen;
			}).collect(Collectors.toList()));
		}
		if(!source.getVideos().isEmpty()) {
			destination.setVideos(source.getVideos().stream().map(v -> {
				Video video = new Video();
				video.setUrl(v.getUrl());
				video.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
				return video;
			}).collect(Collectors.toList()));
		}
		return destination;
	}

}
