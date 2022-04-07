package pe.confianza.colaboradores.gcontenidos.server;

import java.net.UnknownHostException;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestorContenidosServerApplication {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(GestorContenidosServerApplication.class, args);
	}

}
