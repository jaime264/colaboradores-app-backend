package pe.confianza.colaboradores.gcontenidos.server;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestorContenidosServerApplication {

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(GestorContenidosServerApplication.class, args);
	}

}
