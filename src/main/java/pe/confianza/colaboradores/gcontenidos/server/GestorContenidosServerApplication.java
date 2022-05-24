package pe.confianza.colaboradores.gcontenidos.server;

import java.net.UnknownHostException;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;
import pe.confianza.colaboradores.gcontenidos.server.negocio.impl.VacacionesTareasProgramadasNegociompl;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@SpringBootApplication
@EnableScheduling
public class GestorContenidosServerApplication {
	
	@Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(Constantes.FORMATO_FECHA_HORA);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA_HORA)));
        };
    }

	public static void main(String[] args) throws UnknownHostException {
		ApplicationContext applicationContext = SpringApplication.run(GestorContenidosServerApplication.class, args);
		iniciarVacacionesActualizacion(applicationContext);
	}
	
	private static void iniciarVacacionesActualizacion(ApplicationContext applicationContext) {
		VacacionesTareasProgramadasNegocio vacacioneTareas = applicationContext.getBean(VacacionesTareasProgramadasNegociompl.class);
		vacacioneTareas.actualizarEstadoProgramaciones();
		vacacioneTareas.actualizarPeridos();
	}

}
