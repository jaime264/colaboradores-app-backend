package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.ComentarioDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.ImagenDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.PublicacionAppDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.VideoDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Video;

@Service
public class PublicacionAppServiceImpl implements PublicacionAppService{

	
	@Autowired
	PublicacionAppDao publicacionAppDao;
	
	@Autowired
	ImagenDao imagenDao;
	
	@Autowired
	VideoDao videoDao;
	
	@Autowired
	ComentarioDao comentarioDao;
	
	
	@Override
	public List<Publicacion> list() {
		// TODO Auto-generated method stub
				
		return publicacionAppDao.findAll();

	}

	@Override
	public ResponseStatus add(Publicacion publicacion) {
		ResponseStatus status = new ResponseStatus();
		try {
			
			publicacion.setFecha(LocalDateTime.now());
			publicacion.setFechaCrea(LocalDate.now());
			publicacion.setActivo(true);
			
			Publicacion pub = publicacionAppDao.save(publicacion);
			
			pub.getImagenes().forEach(e->{
				Imagen imagen = new Imagen();
				imagen.setPublicacion(pub);
				imagen.setFechaCrea(LocalDate.now());
				imagen.setUrl(e.getUrl());
				imagen.setUsuarioCrea(pub.getUsuarioCrea());
				imagenDao.save(imagen);
			});
			
			pub.getVideos().forEach(e->{
				Video video = new Video();
				video.setPublicacion(pub);
				video.setFechaCrea(LocalDate.now());
				video.setUrl(e.getUrl());
				video.setUsuarioCrea(pub.getUsuarioCrea());
				videoDao.save(video);
			});
			
			status.setCodeStatus(200);
		} catch (Exception e) {
			status.setCodeStatus(500);
		}
		return status;
	}

	@Override
	public ResponseStatus update(Publicacion publicacion) {
		// TODO Auto-generated method stub
		ResponseStatus status = new ResponseStatus();
		
		Optional<Publicacion> pub = publicacionAppDao.findById(publicacion.getId());
		if (pub.isPresent()) {
			
			pub.get().setCategoria(publicacion.getCategoria());
			pub.get().setDescripcion(publicacion.getDescripcion());
			pub.get().setFechaFin(publicacion.getFechaFin());
			pub.get().setFechaInicio(publicacion.getFechaInicio());
			pub.get().setFechaModifica(LocalDate.now());
			pub.get().setFlagReacion(publicacion.getFlagReacion());
			pub.get().setFlagAprobacion(publicacion.getFlagAprobacion());
			pub.get().setMenu(publicacion.getMenu());
			pub.get().setReacciones(publicacion.getReacciones());
			pub.get().setUsuarioModifica(publicacion.getUsuarioModifica());
			
			for(Imagen img : publicacion.getImagenes()) {
				Optional<Imagen> ig = imagenDao.findById(img.getId());
				
				ig.get().setPublicacion(publicacion);
				ig.get().setFechaModifica(LocalDate.now());
				ig.get().setUsuarioModifica(publicacion.getUsuarioModifica());
				ig.get().setUrl(img.getUrl());
				imagenDao.save(ig.get());
			}
			
			for(Video vdo : publicacion.getVideos()) {
				Optional<Video> vd = videoDao.findById(vdo.getId());
				
				vd.get().setPublicacion(publicacion);
				vd.get().setFechaModifica(LocalDate.now());
				vd.get().setUsuarioModifica(publicacion.getUsuarioModifica());
				vd.get().setUrl(vdo.getUrl());
				videoDao.save(vd.get());
			}
			
			
			publicacionAppDao.save(pub.get());
			status.setCodeStatus(200);
		}else {
			status.setCodeStatus(200);
		}

		return status;
	}

	@Override
	public ResponseStatus delete(Long idPublicacion) {
		// TODO Auto-generated method stub
		ResponseStatus status = new ResponseStatus();
		Optional<Publicacion> pub = publicacionAppDao.findById(idPublicacion);
		
		if(pub.isPresent()) {
			pub.get().setActivo(false);
			publicacionAppDao.save(pub.get());
		}
		
		status.setCodeStatus(200);

		return status;
	}

	@Override
	public Publicacion publicacionById(Long id) {
		// TODO Auto-generated method stub
		Optional<Publicacion> pub =  publicacionAppDao.findById(id);
		
		if(pub.isPresent()) {
			return pub.get();
		}
		
		return null;
	}

	
	

}
